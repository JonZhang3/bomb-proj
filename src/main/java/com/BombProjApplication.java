package com;

import com.queryflow.accessor.AccessorFactory;
import com.queryflow.accessor.AccessorFactoryBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BombProjApplication {

    public static void main(String[] args) {
        SpringApplication.run(BombProjApplication.class, args);
    }

    @Bean
    public AccessorFactory configDb() {
        AccessorFactoryBuilder builder = new AccessorFactoryBuilder();
        builder.fromFile();
        return builder.build();
    }

}
