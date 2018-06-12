package com.feng.authserver;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptPasswordGenerator {

    @Test
    public void generate() {
        System.err.println(new BCryptPasswordEncoder().encode("changeit"));
    }

}
