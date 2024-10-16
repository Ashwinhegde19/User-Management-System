package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;


@CrossOrigin(origins = "http://localhost:3000") 
@RestController
public class UserController {
    @Autowired
    private UserService userService;

 // Retrieve all users
    @GetMapping("/users")
    public List<User> list() {
        return userService.listAll();
    }


    // Retrieve a specific user by ID
    @GetMapping("/users/{id}")
    public ResponseEntity<User> get(@PathVariable("id") Integer id) {
        try {
            User user = userService.get(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Create a new user
    @PostMapping("/users")
    public ResponseEntity<Void> add(@RequestBody User user) {
        userService.save(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // Update an existing user
    @PutMapping("/users/{id}")
    public ResponseEntity<Void> update(@RequestBody User user, @PathVariable("id") Integer id) {
        try {
            User existingUser = userService.get(id);
            if (existingUser != null) {
                existingUser.setFirstName(user.getFirstName());
                existingUser.setLastName(user.getLastName());
                existingUser.setType(user.getType());
                existingUser.setPassword(user.getPassword());
                existingUser.setEmail(user.getEmail());

                userService.save(existingUser);

                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete a user by ID
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
