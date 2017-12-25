package com.enginizer.rest.service;

import com.enginizer.rest.model.dto.UserDTO;
import com.enginizer.rest.model.entity.User;
import com.enginizer.rest.repository.UserRepository;
import com.enginizer.rest.service.exception.ResourceNotFound;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User getUserById(Long userID) {
        Optional<User> user = userRepository.findById(userID);

        if (!user.isPresent()) {
            throw new ResourceNotFound(User.class);
        }

        return user.get();
    }

    public UserDTO createUser(UserDTO user) {
        User savedUser = userRepository.save(modelMapper.map(user, User.class));
        return modelMapper.map(savedUser, UserDTO.class);
    }

    public void deleteUserById(Long userID) {
        userRepository.deleteById(userID);
    }
}
