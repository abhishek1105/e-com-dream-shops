package com.freeCodeCamp.dream_shops.data;

import com.freeCodeCamp.dream_shops.model.User;
import com.freeCodeCamp.dream_shops.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {
    private final UserRepo userRepo;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        createDefaultUserIfNotExist();
    }

    private void createDefaultUserIfNotExist() {
        for (int i = 1; i <= 5; i++) {
            String defaultEmail = "user" + i + "@email.com";
            if (userRepo.existsByEmail(defaultEmail)) {
                continue;

            }
            User user = new User();
            user.setFirstName("User " + i);
            user.setLastName(i + " User");
            user.setEmail(defaultEmail);
            user.setPassword("123456");
            userRepo.save(user);
            System.out.println("default user created successfully");

        }
    }
}
