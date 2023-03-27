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

    public String submission(InputRequestDto requestDto, Long questionId) throws SQLException {

        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND));

        List<InputOutput> inputOutput = inputOutputRepository.findByQuestionId(question.getId());

        String userCode = requestDto.getInput();
        System.out.println("userCode : " + userCode);
        // String fileName = Integer.toString(userCode.hashCode());
        String fileName = "Main";
        String filePath = String.format("/home/ubuntu/onlineJudge/temp/%s.%s", fileName, requestDto.getLang());
        File userFile = new File(filePath);
        boolean isPassed = false;
        // thread-safe
        StringBuffer errorLog = new StringBuffer();
        StringBuilder sb = new StringBuilder();
        String DBinput = "";

        
        // Create the userfile
        System.out.println("Create the userfile");
        try {
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
        String query = "SELECT * FROM input_value WHERE question_id = ?";

         // Execute a query to get the contents of the database
         try (
            Connection conn = DriverManager.getConnection(url, user, password);
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setLong(1, questionId);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                sb.append(rs.getString("input")).append("\n");
            }
            DBinput = sb.toString();
            System.out.println("DBinput :" + DBinput);

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

       

        try {
            // Pass the contents as a parameter to the command executed by ProcessBuilder
            System.out.println("Build the command as a list of strings");
            ProcessBuilder pb = new ProcessBuilder("/home/ubuntu/" + langFile, userFile.getAbsolutePath(), DBinput);
            pb.directory(new File("/home/ubuntu/"));

            pb.redirectErrorStream(true);

            // Start the process
            System.out.println("Start the process");
            Process process = pb.start();

            // Read the output from the process  
            System.out.println("Read the output from the process");
            try               
                (InputStream inputStream = process.getInputStream();
                InputStream stderr = process.getErrorStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                BufferedReader errReader = new BufferedReader(new InputStreamReader(stderr));) {
                String line = "";
                List<String> answer = new ArrayList<>();

                for (int i = 0; i < inputOutput.size(); i++) {
                    answer.add(inputOutput.get(i).getOutputs().toString());
                }
                

                if (inputStream == null || inputStream.available() == 0) {
                    System.out.println("No data in inputstream.");
                }

                while ((line = errReader.readLine()) != null) {
                    errorLog.append(line).append("\n");
                    System.out.println("line : " + line);
                }

                if (errorLog.length() == 0) {
                    int index = 0;
                    isPassed = true;
                    System.out.println("Entering reader while");
                    while ((line = reader.readLine()) != null) {
                        System.out.println("line : " + line);
                        errorLog.append(line).append("\n");
                        if (answer != null && !answer.isEmpty() && !answer.get(index).equals(line)) {
                            System.out.println("answer : " + answer);
                            System.out.println("line : " + line);
                            isPassed = false;
                            break;
                        }
                        index++;
                    }
                }

                // Wait for the process to complete and check the exit value
                System.out.println("Wait for the process to complete and check the exit value");
                int exitValue = process.waitFor();
                if (exitValue != 0) {
                    System.out.println("Command exited with error code: " + exitValue);
                }


            } catch (Exception e) {
                e.printStackTrace();
            }

            if (isPassed) {
                return "Test Success";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // send above errorlog to user.
        return "Test Failed " + errorLog.toString();
    }
}
