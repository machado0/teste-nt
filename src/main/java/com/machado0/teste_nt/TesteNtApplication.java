package com.machado0.teste_nt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.machado0.teste_nt.config")
public class TesteNtApplication {

    public static void main(String[] args) {
        SpringApplication.run(TesteNtApplication.class, args);
    }

}
