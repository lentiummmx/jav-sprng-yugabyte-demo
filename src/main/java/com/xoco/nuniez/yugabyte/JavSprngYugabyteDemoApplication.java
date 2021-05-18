package com.xoco.nuniez.yugabyte;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class JavSprngYugabyteDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavSprngYugabyteDemoApplication.class, args);
    }

}
