package com.example.notify.config;

import com.example.notify.models.User;
import com.example.notify.models.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class Bootstrap {
    private final UserRepository userRepository;

    public Bootstrap(UserRepository userRepository) {
        this.userRepository = userRepository;
        createUser();
    }

    public void createUser() {
        User user1 = new User();
        user1.setUsername("max@gmail.com");
        user1.setFullName("Max Mustermann");
        user1.setPassword("max");
        userRepository.save(user1);

        User user2 = new User();
        user2.setUsername("john@gmail.com");
        user2.setFullName("John Doe");
        user2.setPassword("john");
        var entity = userRepository.save(user2);

        var data = userRepository.findById(entity.getId());
        if (data.isPresent()) {
            log.info("user2: {}", data.get());
        } else {
            log.info("no user2");
        }
    }
}
