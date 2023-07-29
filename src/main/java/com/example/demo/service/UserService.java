package com.example.demo.service;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.Exception.CustomException;
import com.example.demo.Exception.ErrorCode;
import com.example.demo.dto.UserRequestDto;
import com.example.demo.model.Role;
import com.example.demo.model.Users;
import com.example.demo.redis.RedisUtil;
import com.example.demo.repository.UsersRepository;
import com.example.demo.security.jwt.JwtProperties;
import com.example.demo.security.jwt.JwtTokenProvider;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // final이 붙거나 @NotNull 이 붙은 필드의 생성자를 자동 생성
public class UserService {
    private final UsersRepository usersRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisUtil redisUtil;

    public void join(UserRequestDto dto) {
        Users userCheck = usersRepository.findByUsername(dto.getUsername()).orElseThrow(
                () -> new CustomException(ErrorCode.DUPLICATE_USERNAME));
            
        Users users = new Users(userCheck.getUsername(), passwordEncoder.encode(dto.getPassword()), dto.getEmail(),
                Role.ROLE_USERS);

        usersRepository.save(users);
    }

    public String refresh(HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {
        // AccessToken
        String expiredAccessTokenHeader = request.getHeader(JwtProperties.HEADER_ACCESS);

        if (expiredAccessTokenHeader == null || !expiredAccessTokenHeader.startsWith(JwtProperties.TOKEN_PREFIX)) {
            throw new CustomException(ErrorCode.EMPTY_CONTENT);
        }
        String expiredAccessTokenName = jwtTokenProvider.getExpiredAccessTokenPk(expiredAccessTokenHeader);
        // refreshToken
        String authorizationHeader = redisUtil.getData(expiredAccessTokenName + JwtProperties.HEADER_REFRESH);

        // 최신 토큰인지 검사
        if (!redisUtil.getData(expiredAccessTokenName + JwtProperties.HEADER_ACCESS).equals(expiredAccessTokenHeader)) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }

        // Refresh Token 유효성 검사
        jwtTokenProvider.validateToken(authorizationHeader);
        String username = jwtTokenProvider.getUserPk(authorizationHeader);
        Users users = usersRepository.findByUsername(username).orElseThrow(
                () -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        Long userId = users.getId();
        // Access Token 재발급
        String accessToken = jwtTokenProvider.createToken(username, userId);

        // 현재시간과 Refresh Token 만료날짜를 통해 남은 만료기간 계산 (만료기간 전 재발급이 필요없다면 삭제)
        // Refresh Token 만료시간 계산해 특정 시간 미만일 시 refresh token 도 재발급
        Date now = new Date();
        Date refreshExpireTime = jwtTokenProvider.ExpireTime(authorizationHeader);
        if (refreshExpireTime.before(new Date(now.getTime() + 1000 * 60 * 60 * 24L))) { // refresh token 만료시간이 특정시간보다
                                                                                        // 작으면 재발급
            String newRefreshToken = jwtTokenProvider.createRefreshToken(username);
            // accessTokenResponseMap.put(JwtProperties.HEADER_REFRESH,
            // JwtProperties.TOKEN_PREFIX + newRefreshToken);
            redisUtil.setDataExpire(jwtTokenProvider.getUserPk(accessToken) + JwtProperties.HEADER_ACCESS,
                    JwtProperties.TOKEN_PREFIX + accessToken, JwtProperties.ACCESS_EXPIRATION_TIME);
            redisUtil.setDataExpire(jwtTokenProvider.getUserPk(accessToken) + JwtProperties.HEADER_REFRESH,
                    JwtProperties.TOKEN_PREFIX + newRefreshToken, JwtProperties.REFRESH_EXPIRATION_TIME);
        }

        response.setHeader(JwtProperties.HEADER_ACCESS, JwtProperties.TOKEN_PREFIX + accessToken);
        return "재발급 성공";
    }

}
