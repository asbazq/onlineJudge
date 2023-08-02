package com.example.demo.service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import com.example.demo.Exception.CustomException;
import com.example.demo.Exception.ErrorCode;
import com.example.demo.dto.InputRequestDto;
import com.example.demo.model.ExecutedCode;
import com.example.demo.model.InputOutput;
import com.example.demo.model.ProblemHistory;
import com.example.demo.model.Question;
import com.example.demo.model.Users;
import com.example.demo.repository.ExecutedCodeRepository;
import com.example.demo.repository.InputOutputRepository;
import com.example.demo.repository.ProblemHistoryRepository;
import com.example.demo.repository.QuestionRepository;
import com.example.demo.repository.UsersRepository;
import com.example.demo.security.UserDetailsImpl;

import java.sql.*;
import java.time.LocalDateTime;
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
    private final ExecutedCodeRepository executedCodeRepository;
    private final UsersRepository usersRepository;

    public String submission(InputRequestDto requestDto, Long questionId, UserDetailsImpl userDetailsImpl)
            throws SQLException, IOException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Users users = usersRepository.findByUsername(userDetailsImpl.getUsername()).orElseThrow(
                () -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND));

        List<InputOutput> inputOutput = inputOutputRepository.findByQuestionId(questionId);

        String userCode = requestDto.getInput();
        log.info("userCode : " + userCode);
        int hashCode = userCode.hashCode();
        log.info("userCode hashing : " + hashCode);
        String dirName = Integer.toString(hashCode);

        // if the code has been executed before, the previous log output 
        List<ExecutedCode> executedCodes = executedCodeRepository.findByQuestionAndUsersAndCode(question,
                users, hashCode);
        if (!executedCodes.isEmpty()) {
            return executedCodes.get(0).getResult();
        }

        String fileName = "Main";
        String filePath = String.format("/home/ubuntu/onlineJudge/" + "%s/%s.%s", dirName, fileName,
                requestDto.getLang());
        String classPath = String.format("/home/ubuntu/onlineJudge/" + "%s/%s.%s", dirName, fileName, "class");
        File userFile = new File(filePath);
        File classFile = new File(classPath);
        boolean isPassed = true;
        StringBuffer errorLog = new StringBuffer(); // thread-safe

        // Create the userfile
        log.info("Create the userfile");
        try {
            Files.createDirectories(Paths.get("/home/ubuntu/onlineJudge/" + dirName));
            userFile.createNewFile();
            Files.writeString(Paths.get(filePath), userCode);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.FILE_CREATION_ERROR, e.getMessage());
        }

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
                throw new CustomException(ErrorCode.CHMOD_ERROR, "Command exited with error code: " + exitValue);
            }
        } catch (Exception e) {
            throw new CustomException(ErrorCode.CHMOD_ERROR, e.getMessage());
        }

        // Pass the contents as a parameter to the command executed by ProcessBuilder
        log.info("Build the command as a list of strings");
        for (int i = 0; i < inputOutput.size(); i++) {
            try {
                ProcessBuilder pb = new ProcessBuilder("/home/ubuntu/converter.sh", userFile.getAbsolutePath(),
                        requestDto.getLang(),
                        inputOutput.get(i).getInput());
                log.info(userFile.getAbsolutePath());
                pb.directory(new File("/home/ubuntu/"));
                pb.redirectErrorStream(true);

                // Start the process
                log.info("Start the process times : " + (i + 1));
                Process process = pb.start();

                // Read the output from the process
                log.info("Read the output from the process");
                try (
                        InputStream inputStream = process.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));) {

                    log.info("Entering reader while");
                    String line = "";
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

                } catch (Exception e) {
                    throw new CustomException(ErrorCode.PROCESS_EXECUTION_ERROR, e.getMessage());
                }
                // Wait for the process to complete and check the exit value
                log.info("Wait for the process to complete and check the exit value");
                int exitValue = process.waitFor();
                if (exitValue != 0) {
                    log.info("Command exited with error code: " + exitValue);
                    throw new CustomException(ErrorCode.PROCESS_EXECUTION_ERROR,
                            "Command exited with error code: " + exitValue);
                }
            } catch (Exception e) {
                throw new CustomException(ErrorCode.PROCESS_EXECUTION_ERROR, e.getMessage());
            }
        }

        // delete file, class, directory
        try {
            boolean fileDelete = userFile.delete();
            boolean classDelete = classFile.delete();
            File dir = new File(String.format("/home/ubuntu/onlineJudge/" + dirName));
            boolean dirDelete = dir.delete();
            log.info("userFile Delete : " + fileDelete + "\n" + "classFile Delete : " + classDelete);
            log.info("userDirectory Delete : " + dirDelete);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.FILE_DELETION_ERROR, e.getMessage());
        }

        if (isPassed) {
            // if ispassed is true, the usercode is stored in the problemHistory

            ProblemHistory problemHistory = ProblemHistory.builder()
                    .code(userCode)
                    .question(question)
                    .users(users)
                    .build();

            problemHistoryRepository.save(problemHistory);

            // save the success output of executed code
            ExecutedCode executedCode = ExecutedCode.builder()
                    .code(hashCode)
                    .result("Test Success")
                    .question(question)
                    .users(users)
                    .build();

            executedCodeRepository.save(executedCode);

            stopWatch.stop();
            log.info("정답 제출 수행시간 >> {}", stopWatch.getTotalTimeSeconds());
            return "Test Success";
        }

        // save the failed output of executed code
        ExecutedCode executedCode = ExecutedCode.builder()
                .code(hashCode)
                .result("Test Failed ErrorLog : \n" + errorLog.toString())
                .question(question)
                .users(users)
                .build();

        executedCodeRepository.save(executedCode);

        // send above errorlog to user.
        stopWatch.stop();
        log.info("정답 제출 수행시간 >> {}", stopWatch.getTotalTimeSeconds());
        return "Test Failed ErrorLog : \n" + errorLog.toString();
    }

     // 1day (24hours = 24 * 60 * 60 * 1000 ms)
    @Scheduled(fixedRate = 24 * 60 * 60 * 1000)
    public void deleteExpiredResult() {
        LocalDateTime expirationDate = LocalDateTime.now().minusDays(1);

        executedCodeRepository.dedeleteByCreateAtBefore(expirationDate);

    }
}
