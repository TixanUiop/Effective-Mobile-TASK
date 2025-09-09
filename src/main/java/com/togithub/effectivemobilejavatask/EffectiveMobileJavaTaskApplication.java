package com.togithub.effectivemobilejavatask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;


// https://github.com/PaatoM/Bank_REST
// https://forms.yandex.ru/u/68220273e010db1881d1132d/
@SpringBootApplication
@EnableMethodSecurity(prePostEnabled = true)
public class EffectiveMobileJavaTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(EffectiveMobileJavaTaskApplication.class, args);
    }

}
