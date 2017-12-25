package com.enginizer.rest.resources;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import com.enginizer.rest.model.dto.UserDTO;
import com.enginizer.rest.model.entity.User;
import com.enginizer.rest.service.UserService;
import com.enginizer.rest.service.exception.ResourceNotFound;
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
    public Page<User> getAllUsers(Pageable pageable) {
        return userService.getAllUsers(pageable);
    }

    /**
     * Retrieves a specific {@link User} by it's URI.
     *
     * @return the {@link User} identified by the URI.
     */
    @GetMapping(value = "/{id}")
    public Resource<UserDTO> getUserById(@PathVariable("id") Long id) {

        User userById = userService.getUserById(id);

        if (null == userById) {
            throw new ResourceNotFound(User.class);
        }

        UserDTO dto = modelMapper.map(userById, UserDTO.class);
        Resource<UserDTO> userResource = new Resource<>(dto);
        ControllerLinkBuilder linkTo = linkTo(
                methodOn(this.getClass()).getAllUsers(null));
        userResource.add(linkTo.withRel("all-users"));

        return userResource;
    }

    /**
     * Creates a new {@link User}.
     *
     * @return 201 is successful.
     */
    @PutMapping
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
    public ResponseEntity<User> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok().build();
    }

}

