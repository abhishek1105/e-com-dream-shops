package com.freeCodeCamp.dream_shops.data;

import com.freeCodeCamp.dream_shops.model.Role;
import com.freeCodeCamp.dream_shops.model.User;
import com.freeCodeCamp.dream_shops.repo.RoleRepo;
import com.freeCodeCamp.dream_shops.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Component
@Transactional
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Set<String> defaultRoles = Set.of("ROLE_ADMIN", "ROLE_USER");
        createDefaultUserIfNotExist();
        createDefaultRoleIfNotExists(defaultRoles);
        createDefaultAdminIfNotExist();
    }

    private void createDefaultUserIfNotExist() {
        Role userRole = roleRepo.findByName("ROLE_USER").get();
        for (int i = 1; i <= 5; i++) {
            String defaultEmail = "user" + i + "@email.com";
            if (userRepo.existsByEmail(defaultEmail)) continue;

            User user = new User();
            user.setFirstName("User " + i);
            user.setLastName(i + " User");
            user.setEmail(defaultEmail);
            user.setPassword(passwordEncoder.encode("123456"));
            user.setRoles(Set.of(userRole));
            userRepo.save(user);
            System.out.println("default user created successfully");
        }
    }

    private void createDefaultAdminIfNotExist() {
        Role adminRole = roleRepo.findByName("ROLE_ADMIN").get();
        for (int i = 1; i <= 2; i++) {
            String defaultEmail = "admin" + i + "@email.com";
            if (userRepo.existsByEmail(defaultEmail)) continue;

            User admin = new User();
            admin.setFirstName("Admin " + i);
            admin.setLastName(i + " Admin");
            admin.setEmail(defaultEmail);
            admin.setPassword(passwordEncoder.encode("123456"));
            admin.setRoles(Set.of(adminRole));
            userRepo.save(admin);
            System.out.println("default admin created successfully");
        }
    }

    private void createDefaultRoleIfNotExists(Set<String> roles) {
        roles.stream().filter(role -> roleRepo.findByName(role).isEmpty()).map(Role::new).forEach(roleRepo::save);

    }

}
