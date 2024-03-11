package com.dietideals.dietideals24_25;

import com.dietideals.dietideals24_25.domain.entities.ApplicationUser;
import com.dietideals.dietideals24_25.domain.entities.Role;
import com.dietideals.dietideals24_25.repositories.ApplicationUserRepository;
import com.dietideals.dietideals24_25.repositories.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@SpringBootApplication
public class DietiDeals2425Application{

    public static void main(String[] args) {
        SpringApplication.run(DietiDeals2425Application.class, args);
    }

    @Bean
    CommandLineRunner run(RoleRepository roleRepository, ApplicationUserRepository applicationUserRepository, PasswordEncoder passwordEncoder){
        return args -> {
            if(roleRepository.findByAuthority("ADMIN").isPresent()) return;
            Role adminRole = roleRepository.save(new Role("ADMIN"));
            roleRepository.save(new Role("USER"));

            Set<Role> roles = new HashSet<>();
            roles.add(adminRole);

            ApplicationUser admin = new ApplicationUser(UUID.randomUUID(), "admin", passwordEncoder.encode("pass"), roles);
            applicationUserRepository.save(admin);
        };
    }

}
