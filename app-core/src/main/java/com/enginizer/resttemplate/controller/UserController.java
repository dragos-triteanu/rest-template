package com.enginizer.resttemplate.controller;

import com.enginizer.resttemplate.model.User;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${app.baseURL}/users")
public class UserController {

    @Autowired
    Environment environment;

    @GetMapping
    public List<User> getUsers() {
        return Arrays.asList(new User("asa", "y"));
    }

}
