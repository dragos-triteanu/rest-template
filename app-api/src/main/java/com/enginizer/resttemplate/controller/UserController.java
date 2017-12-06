package com.enginizer.resttemplate.controller;

import com.enginizer.resttemplate.entity.User;
import java.util.Arrays;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${app.baseURL}/users")
public class UserController {

    @GetMapping
    public List<User> getUsers() {
        return Arrays.asList(new User("asa", "y"));
    }

}
