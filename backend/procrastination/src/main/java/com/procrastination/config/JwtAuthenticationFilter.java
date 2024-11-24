package com.procrastination.config;

import com.nimbusds.jose.JOSEException;
import com.procrastination.exception.jwt.JwtProcessingException;
import com.procrastination.security.CustomUserDetailsService;
import com.procrastination.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.text.ParseException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = resolveToken(request);

        try {
            if (StringUtils.hasText(token) && jwtService.isValidateToken(token)) {
                SecurityContextHolder.getContext().setAuthentication(jwtService.getAuthentication(token));
            }
            filterChain.doFilter(request, response);
        } catch (ParseException e) {
            throw new JwtProcessingException("JWT 파싱 중 오류가 발생했습니다.");
        } catch (JOSEException e) {
            throw new JwtProcessingException("JWT 암호화/복호화 중 오류가 발생했습니다.");
        }
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // "Bearer " 이후의 토큰 반환
        }
        return null;
    }
}
