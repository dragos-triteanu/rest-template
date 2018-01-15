package com.enginizer.rest.resources;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import com.enginizer.rest.model.dto.UserDTO;
import com.enginizer.rest.model.entity.User;
import com.enginizer.rest.model.entity.View;
import com.enginizer.rest.service.UserService;
import com.enginizer.rest.service.exception.ResourceNotFound;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.net.URI;
import javax.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
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
@Api(value = "UserResource", description = "User API", basePath = "${app.baseURL}/users", tags = {
        "User"})
public class UserController {

    @Autowired
    private Environment environment;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Retrieves all the {@link User} with paging and sorting.
     *
     * @param pageable the paging configuration
     * @return a {@link Page} of {@link User}
     */
    @GetMapping
    @ApiOperation(value = "Get All Users", notes = "Retrieves all the users with paging and sorting.", tags = {
            "User"})
    public Page<User> getUsers(Pageable pageable) {
        return userService.getAllUsers(pageable);
    }

    /**
     * Retrieves a specific {@link User} by it's URI.
     *
     * @return the {@link User} identified by the URI.
     */
    @GetMapping(value = "/{id}")
    @ApiOperation(value = "Get User by Id", notes = "Retrieves a User resource by the specified URI.", tags = {
            "User"})
    public Resource<UserDTO> getUserById(
            @ApiParam()
            @PathVariable("id") Long id) {

        User userById = userService.getUserById(id);

        if (null == userById) {
            throw new ResourceNotFound(User.class);
        }

        UserDTO dto = modelMapper.map(userById, UserDTO.class);
        Resource<UserDTO> userResource = new Resource<>(dto);
        ControllerLinkBuilder linkTo = linkTo(
                methodOn(this.getClass()).getUsers(null));
        userResource.add(linkTo.withRel("all-users"));

        return userResource;
    }

    /**
     * Retrieves a specific {@link User} id by it's URI.
     * This is a good example of filtering
     *
     * @return the URI of the {@link User} identified by the URI.
     */
    @GetMapping(value = "/{id}/summary")
    @ApiOperation(value = "Get User by Id", notes = "Retrieves a User resource by the specified URI.", tags = {
            "User"})
    @JsonView(View.Summary.class)
    public User getFilteredUserId(
            @ApiParam()
            @PathVariable("id") Long id) {

        User userById = userService.getUserById(id);

        if (null == userById) {
            throw new ResourceNotFound(User.class);
        }


//      TODO find a way of making @JsonView work with Resources
//      https://www.udemy.com/microservices-with-spring-boot-and-spring-cloud/learn/v4/t/lecture/8005676?start=390
//      Resource<User> userResource = new Resource<>(userById);
//      ControllerLinkBuilder linkTo = linkTo(
//      methodOn(this.getClass()).getUsers(null));
//      userResource.add(linkTo.withRel("all-users"));

        return userById;
    }


    /**
     * Creates a new {@link User}.
     *
     * @return 201 is successful.
     */
    @PutMapping
    @ApiOperation(value = "Create User", notes = "Creates a new User", tags = {"User"})
    public ResponseEntity<UserDTO> createUser(
            @ApiParam(name = "user", required = true)
            @RequestBody
            @Valid UserDTO user) {
        UserDTO createdUser = userService.createUser(user);
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
    @ApiOperation(value = "Delete User", notes = "Delete a User Resource by it's URI", tags = {
            "User"})
    public ResponseEntity<User> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok().build();
    }

}

