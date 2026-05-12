package com.product.demo.config;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoder {

    // 비밀번호 암호화
    public String encode(String rawPassword) {

        return BCrypt.withDefaults()
                .hashToString(BCrypt.MIN_COST, rawPassword.toCharArray());

    }

    // 비밀번호 일치 여부 확인하기
    public boolean matches(String rawPassword, String encodedPassword) {
        BCrypt.Result result = BCrypt.verifyer().verify(rawPassword.toCharArray(), encodedPassword);

        return result.verified;
    }
}

