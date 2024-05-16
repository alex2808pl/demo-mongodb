package de.telran.demomongodb.controller;

import de.telran.demomongodb.dao.User;
import de.telran.demomongodb.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/")
public class UserController {

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<User> getAllUsers() {
        LOG.info("Getting all users.");
        return userRepository.findAll();
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public User getUser(@PathVariable String userId) {
        LOG.info("Getting user with ID: {}.", userId);
        return userRepository.findById(userId).orElse(new User());
    }
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public User addNewUsers(@RequestBody User user) {
        LOG.info("Saving user.");
        return userRepository.save(user);
    }

    @RequestMapping(value = "/edit/{userId}", method = RequestMethod.PUT)
    public User addNewUsers(@PathVariable String userId, @RequestBody User userNew) {
        User user = userRepository.findById(userId).orElse(new User());
        user.setName(userNew.getName());
        user.setUserSettings(userNew.getUserSettings());
        LOG.info("Edit user.");
        if (user != null) {
            return userRepository.save(user);
        } else {
            return new User();
        }

    }

    @RequestMapping(value = "/settings/{userId}", method = RequestMethod.GET)
    public Object getAllUserSettings(@PathVariable String userId) {
        User user = userRepository.findById(userId).orElse(new User());
        if (user != null) {
            return user.getUserSettings();
        } else {
            return "User not found.";
        }
    }

    @RequestMapping(value = "/settings/{userId}/{key}", method = RequestMethod.GET)
    public String getUserSetting(@PathVariable String userId, @PathVariable String key) {
        User user = userRepository.findById(userId).orElse(new User());
        if (user != null) {
            return user.getUserSettings().get(key);
        } else {
            return "User not found.";
        }
    }

    @RequestMapping(value = "/settings/{userId}/{key}/{value}", method = RequestMethod.GET)
    public String addUserSetting(@PathVariable String userId, @PathVariable String key, @PathVariable String value) {
        User user = userRepository.findById(userId).orElse(new User());
        if (user != null) {
            user.getUserSettings().put(key, value);
            userRepository.save(user);
            return "Key added";
        } else {
            return "User not found.";
        }
    }
}