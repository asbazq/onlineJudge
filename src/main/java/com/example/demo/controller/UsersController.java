package com.example.demo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.UserRequestDto;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UsersController {
    private final UserService userService;

    @PostMapping("api/join")
    public ResponseEntity<Void> join(@RequestBody UserRequestDto dto) {
        userService.join(dto);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("api/refresh")
    public ResponseEntity<String> refresh(HttpServletRequest request, HttpServletResponse response)
            throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.OK).body(userService.refresh(request, response));
    }

}
