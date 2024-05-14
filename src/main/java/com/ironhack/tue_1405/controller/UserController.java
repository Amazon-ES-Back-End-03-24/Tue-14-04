package com.ironhack.tue_1405.controller;

import com.ironhack.tue_1405.model.User;
import com.ironhack.tue_1405.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    //...localhost::8080/users/1
    //...localhost::8080/users/id/1
    @GetMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User getUserById(@PathVariable(name = "id") Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    //...localhost::8080/users
    //...localhost::8080/users?name="name"
    //...localhost::8080/users?age="age"
    //...localhost::8080/users?age="age"&name="name"
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<User> getUsersByNameAndAge(@RequestParam(required = false, defaultValue = "", name = "name") String userName,
                                     @RequestParam(required = false, name = "age") Optional<Integer> userAge) {
//        if (userName.isEmpty()) {
//            return userRepository.findAll();
//        } else {
//            return userRepository.findByNameContaining(userName);
//        }

        if (userName.isEmpty() && userAge.isEmpty()) {
            return userRepository.findAll();
        }
        else if (!userName.isEmpty() && userAge.isEmpty()) {
            return userRepository.findByNameContaining(userName);
        }
        else if (userName.isEmpty() && userAge.isPresent()) {
            return userRepository.findByAge(userAge.get());
        } else {
            return userRepository.findByNameAndAge(userName, userAge.get());
        }
    }
}
