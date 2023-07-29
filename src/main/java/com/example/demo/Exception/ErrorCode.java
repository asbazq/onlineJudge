package com.example.demo.Exception;


import org.springframework.http.HttpStatus;
import lombok.Getter;

@Getter
public enum ErrorCode {

    COMPLETED_OK(HttpStatus.OK,"수행 완료."),
    FILE_CREATION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "파일 생성 중 오류가 발생했습니다."),
    CHMOD_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "파일 권한 중 오류가 발생했습니다."),
    PROCESS_EXECUTION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "프로세스 실행 중 오류가 발생했습니다."),
    FILE_DELETION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "파일 삭제 중 오류가 발생했습니다."),
    USERNAME_LEGNTH(HttpStatus.BAD_REQUEST, "ID를 4자 이상으로 만들어주세요."),
    USERNAME_EMAIL(HttpStatus.BAD_REQUEST, "ID를 이메일 형식으로 만들어주세요."),
    PASSWORD_CONTAINUSERNAME(HttpStatus.BAD_REQUEST, "패스워드에 아이디가 들어갈 수 없습니다."),
    PASSWORD_LEGNTH(HttpStatus.BAD_REQUEST, "패스워드를 8자 이상으로 만들어주세요."),
    PASSWORD_PASSWORDCHECK(HttpStatus.BAD_REQUEST, "패스워드와 패스워드 체크가 맞지 않습니다."),
    MEMBER_HAS_FULL(HttpStatus.CONFLICT, "참여할 수 있는 최대 인원을 초과했습니다."),
    EMPTY_CONTENT(HttpStatus.BAD_REQUEST, "필수 입력값이 없습니다."),
    INVALID_AUTH_TOKEN(HttpStatus.UNAUTHORIZED, "만료되었거나 유효하지 않은 토큰입니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
    INVALID_SIGNATURE_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "만료된 토큰입니다."),
    INVALID_AUTHORITY(HttpStatus.FORBIDDEN, "권한이 없는 사용자입니다."),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 유저 정보를 찾을 수 없습니다."),
    BOARD_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 게시글 정보를 찾을 수 없습니다."),
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 카테고리를 찾을 수 없습니다."),
    AUTH_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "로그인이 필요한 서비스입니다."),
    LANGUAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 언어를 찾을 수 없습니다."),
    DUPLICATE_USERNAME(HttpStatus.CONFLICT, "중복된 사용자명이 존재합니다."),
    DUPLICATE_NICKNAME(HttpStatus.CONFLICT, "중복된 닉네임이 존재합니다."),
    DUPLICATE_APPLY(HttpStatus.CONFLICT, "이미 참여한 매칭입니다."),
    DUPLICATE_REVIEW(HttpStatus.CONFLICT, "이미 참여한 리뷰입니다."),
    FRIENDNAME_OVERLAP(HttpStatus.CONFLICT, "이미 친구로 등록되었습니다."),
    SELF_REGISTRATION(HttpStatus.BAD_REQUEST, "자기 자신을 등록할 수 없습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버에서 요청 사항을 수행할 수 없습니다."),
    ACCEPTED_SEAM(HttpStatus.ACCEPTED, "중복입니다.");

    private final HttpStatus httpStatus;
    private final String errorMessage;

    ErrorCode(HttpStatus httpStatus, String errorMessage) {
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }

}