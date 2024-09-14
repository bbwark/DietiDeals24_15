package com.dietideals.dietideals24_25;

import com.dietideals.dietideals24_25.repositories.UserRepository;
import com.dietideals.dietideals24_25.domain.entities.RoleEntity;
import com.dietideals.dietideals24_25.repositories.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class DietiDeals2425Application {

    public static void main(String[] args) {
        SpringApplication.run(DietiDeals2425Application.class, args);
    }

    @Bean
    CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        return args -> {
            if (roleRepository.count() == 0) {
                roleRepository.save(new RoleEntity("ADMIN"));
                roleRepository.save(new RoleEntity("USER"));
            }
        };
    }

}
