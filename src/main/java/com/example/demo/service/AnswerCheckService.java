package com.example.demo.service;

import java.io.*;

import org.springframework.stereotype.Service;

import com.example.demo.Exception.CustomException;
import com.example.demo.Exception.ErrorCode;
import com.example.demo.dto.InputRequestDto;
import com.example.demo.model.InputOutput;
import com.example.demo.model.Question;
import com.example.demo.repository.InputOutputRepository;
import com.example.demo.repository.QuestionRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // final이 붙거나 @NotNull 이 붙은 필드의 생성자를 자동 생성
public class AnswerCheckService {

    private final QuestionRepository questionRepository;
    private final InputOutputRepository inputOutputRepository;

    public String submission(InputRequestDto requestDto, Long questionId) throws SQLException, IOException {

        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND));

        List<InputOutput> inputOutput = inputOutputRepository.findByQuestionId(question.getId());

        String userCode = requestDto.getInput();
        System.out.println("userCode : " + userCode);
        String dirName = Integer.toString(userCode.hashCode());
        String fileName = "Main";
        String filePath = String.format("/home/ubuntu/onlineJudge/" + dirName + "/%s.%s", fileName,
                requestDto.getLang());
        String classPath = String.format("/home/ubuntu/onlineJudge/" + dirName + "/%s.%s", fileName, "class");
        File userFile = new File(filePath);
        File classFile = new File(classPath);
        File dir = new File(String.format("/home/ubuntu/onlineJudge/" + dirName));
        boolean isPassed = false;
        StringBuffer errorLog = new StringBuffer(); // thread-safe
        StringBuilder sb = new StringBuilder();
        String DBinput = "";
        List<String> DBinputList = new ArrayList<>();
        String line = "";
        String answer = "";
        List<String> answerList = new ArrayList<>();
        StringBuilder asb = new StringBuilder();

        // Create the userfile
        System.out.println("Create the userfile");
        try {
            boolean dirCreate = dir.mkdir();
            System.out.println("directory Create : " + dirCreate);
            userFile.createNewFile();
            FileWriter fw = new FileWriter(userFile);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(userCode);
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Check program language
        System.out.println("Check program language");
        String langFile;
        switch (requestDto.getLang()) {
            case "java":
                langFile = "javac.sh";
                break;
            case "python":
                langFile = "pyconverter.sh";
                break;
            case "c":
                langFile = "cconverter.sh";
                break;
            case "cpp":
                langFile = "cppconverter.sh";
                break;
            default:
                throw new CustomException(ErrorCode.LANGUAGE_NOT_FOUND);
        }

        // Connect to the database
        String url = "jdbc:mysql://judge.cykmfwbvkn4k.ap-northeast-2.rds.amazonaws.com:3306/judge?serverTimezone=UTC&characterEncoding=UTF-8";
        String user = "admin";
        String password = "ideapad330";
        String query = "SELECT * FROM input_output WHERE question_id = ?";

        // Execute a query to get the contents of the database
        try (
                Connection conn = DriverManager.getConnection(url, user, password);
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setLong(1, questionId);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                sb.append(rs.getString("input"));
                DBinput = sb.toString();
                sb.setLength(0); // sb reset
                DBinputList.add(DBinput);
            }

            // Close the database connection
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            // chmod the file
            System.out.println("chmod the userfile");
            ProcessBuilder chmodpb = new ProcessBuilder("chmod", "uo+wx", userFile.getAbsolutePath());
            // start the chmodpb process
            System.out.println("start the chmodpb process");
            Process chmodprocess = chmodpb.start();
            int exitValue = chmodprocess.waitFor();

            // Wait for the chmod process to complete and check the exit value
            System.out.println("Wait for the chmod process to complete and check the exit value");
            if (exitValue != 0) {
                System.out.println("Command exited with error code: " + exitValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Pass the contents as a parameter to the command executed by ProcessBuilder
        System.out.println("Build the command as a list of strings");
        for (int i = 0; i < inputOutput.size(); i++) {
            ProcessBuilder pb = new ProcessBuilder("/home/ubuntu/" + langFile, userFile.getAbsolutePath(),
                    DBinputList.get(i));
            pb.directory(new File("/home/ubuntu/"));

            pb.redirectErrorStream(true);

            // Start the process
            System.out.println("Start the process times : " + (i + 1));
            Process process = pb.start();

            // Read the output from the process
            System.out.println("Read the output from the process");
            try (InputStream inputStream = process.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));) {

                asb.append(inputOutput.get(i).getOutput());
                answer = asb.toString();
                asb.setLength(0); // asb reset
                answerList.add(answer);

                if (errorLog.length() == 0) {
                    isPassed = true;
                }
                System.out.println("Entering reader while");
                while ((line = reader.readLine()) != null) {
                    if (!answerList.get(i).replace("\n", "").equals(line)) {
                        System.out.println("Test " + (i + 1) + " failed.");
                        errorLog.append("Test " + (i + 1) + " Failed").append("\n").append("Input: ")
                                .append(inputOutput.get(i).getInput()).append("\n").append("Expected output: ")
                                .append(inputOutput.get(i).getOutput()).append("\n").append("Your output: ")
                                .append(line.toString()).append("\n");
                        isPassed = false;
                        break;
                    }

                }

                // Wait for the process to complete and check the exit value
                System.out.println("Wait for the process to complete and check the exit value");
                int exitValue = process.waitFor();
                if (exitValue != 0) {
                    System.out.println("Command exited with error code: " + exitValue);
                }

                reader.close();
                // errReader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        boolean fileDelete = userFile.delete();
        boolean classDelete = classFile.delete();
        boolean dirDelete = dir.delete();
        System.out.println("userFile Delete : " + fileDelete + "\n" + "classFile Delete : " + classDelete);
        System.out.println("userDirectory Delete : " + dirDelete);

        if (isPassed) {
            return "Test Success";
        }
        // send above errorlog to user.
        return "Test Failed ErrorLog : \n" + errorLog.toString();
    }
}
