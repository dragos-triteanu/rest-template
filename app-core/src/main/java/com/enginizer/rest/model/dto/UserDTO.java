package com.enginizer.rest.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Email;

@ApiModel(description = "A user model")
public class UserDTO {

    @ApiModelProperty(hidden = true)
    private Long id;

    @ApiModelProperty(notes = "The username of a user as an email")
    @Email
    private String username;

    @ApiModelProperty(notes = "The password of a user")
    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
