package com.example.demo.service;

import java.io.*;

import org.springframework.stereotype.Service;

import com.example.demo.Exception.CustomException;
import com.example.demo.Exception.ErrorCode;
import com.example.demo.dto.InputRequestDto;
import com.example.demo.model.InputOutput;
import com.example.demo.model.Question;
import com.example.demo.reposotory.InputOutputRepository;
import com.example.demo.reposotory.QuestionRepository;

import java.lang.StringBuilder;
import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // final이 붙거나 @NotNull 이 붙은 필드의 생성자를 자동 생성
public class AnswerCheckService {

    private final QuestionRepository questionRepository;
    private final InputOutputRepository inputOutputRepository;

    public String submission(InputRequestDto requestDto, Long questionId) {

        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND));

        InputOutput inputOutput = inputOutputRepository.findById(question.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.EMPTY_CONTENT));

        String userCode = requestDto.getInput();
        String fileName = Integer.toString(userCode.hashCode());
        String filePath = String.format("/home/ubuntu/onlineJudge/temp/_%s.%s", fileName, requestDto.getLang());
        File userFile = new File(filePath);
        String langFile = "";
        StringBuilder sb = new StringBuilder();
        String DBinput = "";
        boolean isPassed = false;
        StringBuilder errorLog = new StringBuilder();

        for (int i = 0; i < inputOutput.getOutput().size(); i++) {
            sb.append(inputOutput.getOutput().get(i));
        }

        DBinput = sb.toString();

        // Create the file
        System.out.println("Create the file");
        try {
            userFile.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Check program language
        System.out.println("Check program language");
        if (requestDto.getLang().equals("java")) {
            langFile = "javac.sh";
        } else if (requestDto.getLang().equals("python")) {
            langFile = "pyconverter.sh";
        } else if (requestDto.getLang().equals("c")) {
            langFile = "cconverter.sh";
        } else {
            langFile = "cppconverter.sh";
        }

        try {
            // chmod the file
            System.out.println("chmod the file");
            ProcessBuilder chmodpb = new ProcessBuilder("chmod", "u+x,o+x", userFile.getAbsolutePath());
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
            // Build the command as a list of strings
            System.out.println("Build the command as a list of strings");
            // ProcessBuilder pb = new ProcessBuilder("./", langFile, filePath, DBinput);
            ProcessBuilder pb = new ProcessBuilder("./"+langFile, userFile.getAbsolutePath(), DBinput);

            pb.redirectErrorStream(true);

            // Start the process
            System.out.println("Start the process");
            Process process = pb.start();

            // Read the output from the process
            System.out.println("Read the output from the process");
            InputStream inputStream = process.getInputStream();
            InputStream stderr = process.getErrorStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            BufferedReader errReader = new BufferedReader(new InputStreamReader(stderr));
            String line = "";
            List<String> answwer = new ArrayList<>();
            while ((line = errReader.readLine()) != null) {
                errorLog.append(line).append("\n");
            }

            if (errorLog.length() == 0) {
                int index = 0;
                answwer = inputOutput.getOutput();
                isPassed = true;
                while ((line = reader.readLine()) != null) {
                    index++;
                    if (!answwer.get(index).equals(line)) {
                        isPassed = false;
                        break;
                    }
                }
            }

            // Wait for the process to complete and check the exit value
            System.out.println("Wait for the process to complete and check the exit value");
            int exitValue = process.waitFor();
            if (exitValue != 0) {
                System.out.println("Command exited with error code: " + exitValue);
            }

            inputStream.close();
            stderr.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (isPassed) {
            return "Test Succes";
        }
        // send above errorlog to user.
        return "Test Failed" + errorLog.toString();
    }
}
