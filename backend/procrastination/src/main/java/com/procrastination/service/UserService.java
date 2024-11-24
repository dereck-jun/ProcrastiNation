package com.procrastination.service;

import com.nimbusds.jose.JOSEException;
import com.procrastination.domain.entity.UserEntity;
import com.procrastination.domain.user.User;
import com.procrastination.domain.user.UserAuthenticationRequest;
import com.procrastination.domain.user.UserAuthenticationResponse;
import com.procrastination.domain.user.UserRegisterRequest;
import com.procrastination.exception.jwt.JwtProcessingException;
import com.procrastination.exception.user.UserAlreadyExistsException;
import com.procrastination.exception.user.UserAuthenticationException;
import com.procrastination.repository.UserEntityRepository;
import com.procrastination.security.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserEntityRepository userEntityRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtService jwtService;

    // 01. 회원가입
    public User register(UserRegisterRequest request) {
        userEntityRepository.findByEmail(request.email()).ifPresent(userEntity -> {
            throw new UserAlreadyExistsException();
        });
        UserEntity registerUser = userEntityRepository.save(
                UserEntity.of(
                        request.email(),
                        passwordEncoder.encode(request.password()),
                        request.phoneNumber()
                )
        );
        return User.from(registerUser);
    }

    // 02. 로그인
    @Transactional(readOnly = true)
    public UserAuthenticationResponse authenticate(UserAuthenticationRequest request) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(request.email());

        if (passwordEncoder.matches(request.password(), userDetails.getPassword())) {
            String accessToken = null;
            try {
                accessToken = jwtService.generateToken(userDetails.getUsername());
            } catch (JOSEException e) {
                throw new JwtProcessingException("JWT 암호화/복호화 중 오류가 발생했습니다.");
            }
            return new UserAuthenticationResponse(accessToken);
        } else {
            throw new UserAuthenticationException();
        }
    }
}
