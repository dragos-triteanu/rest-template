package com.enginizer.resttemplate.controller;

import com.enginizer.resttemplate.model.entity.User;
import com.enginizer.resttemplate.service.UserService;
import java.net.URI;
import java.util.Locale;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("${app.baseURL}/users")
public class UserController {

    @Autowired
    private Environment environment;

    @Autowired
    private UserService userService;

    /**
     * Retrieves all the {@link User} with paging and sorting.
     *
     * @param pageable the paging configuration
     * @return a {@link Page} of {@link User}
     */
    @GetMapping
    public Page<User> getAllUsers(Pageable pageable) {
        return userService.getAllUsers(pageable);
    }

    /**
     * Retrieves a specific {@link User} by it's URI.
     *
     * @return the {@link User} identified by the URI.
     */
    @GetMapping(value = "/{id}")
    public User getUserById(@PathVariable("id") Long id) {
        return userService.getUserById(id);
    }

    /**
     * Creates a new {@link User}.
     *
     * @return 201 is successful.
     */
    @PutMapping
    public ResponseEntity<User> createUser(@RequestBody @Valid User user) {
        User createdUser = userService.createUser(user);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(createdUser.getId()).toUri();
        return ResponseEntity.created(uri).body(createdUser);
    }

    /**
     * Delete a {@link User} entity by it's URI.
     *
     * @param id the URI
     * @return 200 if success
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok().build();
    }

}

