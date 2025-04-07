package com.example.notify.api;

import com.example.notify.models.User;
import com.example.notify.models.UserDto;
import com.example.notify.models.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class UserApi {
    private final UserRepository userRepository;

    public UserApi(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Create
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userRepository.save(user));
    }

    // Read all
    @GetMapping
    public ResponseEntity<Iterable<User>> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> me(Authentication authentication) {
        String userId = authentication.getName();
        var user = userRepository.findById(Long.valueOf(userId)).orElse(new User());
        return ResponseEntity.ok(new UserDto(user.getId(), user.getUsername(), user.getFullName()));
    }

    // Read by ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            User existingUser = user.get();
            existingUser.setUsername(userDetails.getUsername());
            existingUser.setPassword(userDetails.getPassword());
            existingUser.setFullName(userDetails.getFullName());
            return ResponseEntity.ok(userRepository.save(existingUser));
        }
        return ResponseEntity.notFound().build();
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
