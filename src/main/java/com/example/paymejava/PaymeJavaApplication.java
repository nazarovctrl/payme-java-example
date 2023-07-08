package com.example.paymejava;

import com.example.paymejava.entity.OrderEntity;
import com.example.paymejava.repository.OrderRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PaymeJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymeJavaApplication.class, args);
    }

    @Bean
    CommandLineRunner prepare(OrderRepository repository) {
        return args -> {
            repository.save(new OrderEntity(100L, 500000L, true));
            repository.save(new OrderEntity(101L, 10000L, false));
            repository.save(new OrderEntity(102L, 25000L, false));
        };
    }
}
