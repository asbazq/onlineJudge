package com.example.demo.service;

import java.io.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import com.example.demo.Exception.CustomException;
import com.example.demo.Exception.ErrorCode;
import com.example.demo.dto.InputRequestDto;
import com.example.demo.model.InputOutput;
import com.example.demo.model.ProblemHistory;
import com.example.demo.model.Question;
import com.example.demo.model.Users;
import com.example.demo.repository.InputOutputRepository;
import com.example.demo.repository.ProblemHistoryRepository;
import com.example.demo.repository.QuestionRepository;
import com.example.demo.security.UserDetailsImpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor // final이 붙거나 @NotNull 이 붙은 필드의 생성자를 자동 생성
@Slf4j
public class AnswerCheckService {

    private final QuestionRepository questionRepository;
    private final InputOutputRepository inputOutputRepository;
    private final ProblemHistoryRepository problemHistoryRepository;
    
    // database information
    @Value("${spring.datasource.username}")
    String user;
    
    @Value("${spring.datasource.password}")
    String password;

    @Value("${spring.datasource.url}")
    String url;

    public String submission(InputRequestDto requestDto, Long questionId, UserDetailsImpl userDetailsImpl)
            throws SQLException, IOException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND));

        List<InputOutput> inputOutput = inputOutputRepository.findByQuestionId(question.getId());

        String userCode = requestDto.getInput();
        log.info("userCode : " + userCode);
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
        // StringBuilder sb = new StringBuilder();
        // String DBinput = "";
        // List<String> DBinputList = new ArrayList<>();
        String line = "";
        // String answer = "";
        // List<String> answerList = new ArrayList<>();
        // StringBuilder asb = new StringBuilder();


        // Create the userfile
        log.info("Create the userfile");
        try {
            boolean dirCreate = dir.mkdir();
            log.info("directory Create : " + dirCreate);
            userFile.createNewFile();
            FileWriter fw = new FileWriter(userFile);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(userCode);
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // // gets input value from input_output database with the question_id parameter
        // String query = "SELECT * FROM input_output WHERE question_id = ?";

        // // Connect to the database
        // try (
        //         Connection conn = DriverManager.getConnection(url, user, password);
        //         // Execute a query to get the contents of the database
        //         PreparedStatement stmt = conn.prepareStatement(query)) {

        //     stmt.setLong(1, questionId);

        //     ResultSet rs = stmt.executeQuery();
        //     while (rs.next()) {
        //         sb.append(rs.getString("input"));
        //         DBinput = sb.toString();
        //         sb.setLength(0); // sb reset
        //         DBinputList.add(DBinput);
        //     }

        //     // Close the database connection
        //     rs.close();
        //     stmt.close();
        //     conn.close();
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }

        try {
            // chmod the file
            log.info("chmod the userfile");
            ProcessBuilder chmodpb = new ProcessBuilder("chmod", "uo+wx", userFile.getAbsolutePath());
            // start the chmodpb process
            log.info("start the chmodpb process");
            Process chmodprocess = chmodpb.start();
            int exitValue = chmodprocess.waitFor();

            // Wait for the chmod process to complete and check the exit value
            log.info("Wait for the chmod process to complete and check the exit value");
            if (exitValue != 0) {
                log.info("Command exited with error code: " + exitValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Pass the contents as a parameter to the command executed by ProcessBuilder
        log.info("Build the command as a list of strings");
        for (int i = 0; i < inputOutput.size(); i++) {
            ProcessBuilder pb = new ProcessBuilder("/home/ubuntu/converter.sh", userFile.getAbsolutePath(), requestDto.getLang(),
                    inputOutput.get(i).getInput());
            log.info(userFile.getAbsolutePath());
            pb.directory(new File("/home/ubuntu/"));

            pb.redirectErrorStream(true);

            // Start the process
            log.info("Start the process times : " + (i + 1));
            Process process = pb.start();

            // Read the output from the process
            log.info("Read the output from the process");
            try (InputStream inputStream = process.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));) {

                // asb.append(inputOutput.get(i).getOutput());
                // answer = asb.toString();
                // asb.setLength(0); // asb reset
                // answerList.add(answer);

                if (errorLog.length() == 0) {
                    isPassed = true;
                }
                log.info("Entering reader while");
                while ((line = reader.readLine()) != null) {
                    if (!inputOutput.get(i).getOutput().replace("\n", "").equals(line)) {
                        log.info("Test " + (i + 1) + " failed.");
                        errorLog.append("Test " + (i + 1) + " Failed").append("\n").append("Input: ")
                                .append(inputOutput.get(i).getInput()).append("\n").append("Expected output: ")
                                .append(inputOutput.get(i).getOutput()).append("\n").append("Your output: ")
                                .append(line.toString()).append("\n");
                        isPassed = false;
                        break;
                    }

                }

                
                
                // Wait for the process to complete and check the exit value
                log.info("Wait for the process to complete and check the exit value");
                int exitValue = process.waitFor();
                if (exitValue != 0) {
                    log.info("Command exited with error code: " + exitValue);
                }

               reader.close();
                // errReader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // delete file, class, directory
        boolean fileDelete = userFile.delete();
        boolean classDelete = classFile.delete();
        boolean dirDelete = dir.delete();
        log.info("userFile Delete : " + fileDelete + "\n" + "classFile Delete : " + classDelete);
        log.info("userDirectory Delete : " + dirDelete);

        if (isPassed) {

            // if ispassed is true, the usercode is stored in the problemHistory 
            List<Question> qList = new ArrayList<>();
            qList.add(question);
            List<Users> uList = new ArrayList<>();
            uList.add(userDetailsImpl.getUsers());

            ProblemHistory problemHistory = ProblemHistory.builder()
                    .code(userCode)
                    .question(qList)
                    .users(uList)
                    .build();

            problemHistoryRepository.save(problemHistory);

            stopWatch.stop();
            log.info("정답 제출 수행시간 >> {}", stopWatch.getTotalTimeSeconds());
            return "Test Success";
        }
        // send above errorlog to user.
        stopWatch.stop();
        log.info("정답 제출 수행시간 >> {}", stopWatch.getTotalTimeSeconds());
        return "Test Failed ErrorLog : \n" + errorLog.toString();
    }
}
