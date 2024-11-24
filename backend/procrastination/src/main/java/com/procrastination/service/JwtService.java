package com.procrastination.service;

import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jose.crypto.RSAEncrypter;
import com.nimbusds.jwt.EncryptedJWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.procrastination.domain.user.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;
import java.util.Collections;
import java.util.Date;

@Slf4j
@Component
public class JwtService {

    private final RSAPublicKey publicKey;
    private final RSAPrivateKey privateKey;

    @Value("${jwt.expiration}")
    private long expirationTime;

    public JwtService() throws NoSuchAlgorithmException {
        // RSA를 사용한 비대칭키 암호화 방식으로 key 생성
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        this.publicKey = (RSAPublicKey) keyPair.getPublic();
        this.privateKey = (RSAPrivateKey) keyPair.getPrivate();
    }

    // JWE Token Generate
    public String generateToken(String username) throws JOSEException {
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(username)
                .issueTime(new Date())
                .expirationTime(new Date(System.currentTimeMillis() + expirationTime))
                .build();

        // JWE Header 설정 (암호화 알고리즘: AES/GCM)
        JWEHeader header = new JWEHeader.Builder(JWEAlgorithm.RSA_OAEP_256, EncryptionMethod.A256GCM)
                .contentType("JWT")
                .build();

        // EncryptJWT 객체 생성
        EncryptedJWT jwt = new EncryptedJWT(header, claimsSet);

        // public key를 활용한 암호화 수행
        RSAEncrypter encrypt = new RSAEncrypter(publicKey);
        jwt.encrypt(encrypt);

        // 직렬화된 JWE 토큰 반환
        return jwt.serialize();
    }

    // JWE Token Validation & decrypt
    public String validationToken(String token) throws ParseException, JOSEException {
        // 직렬화된 토큰을 EncryptedJWT로 파싱
        EncryptedJWT jwt = EncryptedJWT.parse(token);

        // private key를 활용한 복호화 수행
        RSADecrypter decrypt = new RSADecrypter(privateKey);
        jwt.decrypt(decrypt);

        // Claims에서 사용자 이름 추출
        return jwt.getJWTClaimsSet().getSubject();
    }

    public boolean isValidateToken(String token) throws ParseException, JOSEException {
        try {
            Jwts.parser()
                    .verifyWith(publicKey)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public Authentication getAuthentication(String token) throws ParseException, JOSEException {

        String username = Jwts.parser()
                .verifyWith(publicKey)
                .build()
                .parseSignedClaims(token)
                .getPayload().getSubject();

        return new UsernamePasswordAuthenticationToken(
                username,
                null,
                Collections.singletonList(
                        new SimpleGrantedAuthority(UserRole.USER.getValue())));
    }
}

