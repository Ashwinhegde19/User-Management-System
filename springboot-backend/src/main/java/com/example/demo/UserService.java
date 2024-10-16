package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> listAll() {
        return userRepository.findAll();
    }

    public void save(User user) {
        // Check if the email already exists excluding the current user
        if (userRepository.existsByEmailAndIdNot(user.getEmail(), user.getId())) {
            throw new IllegalArgumentException("Email already exists");
        }
        userRepository.save(user);
    }

    public User get(Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElse(null);
    }

    public void delete(Integer id) {
        userRepository.deleteById(id);
    }
}
