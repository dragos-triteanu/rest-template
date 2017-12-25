package com.enginizer.resttemplate.service;

import com.enginizer.resttemplate.model.entity.User;
import com.enginizer.resttemplate.repository.UserRepository;
import com.enginizer.resttemplate.service.exception.ResourceNotFound;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

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

    public User createUser(User user) {
        User save = userRepository.save(user);
        return save;
    }

    public void deleteUserById(Long userID){
        userRepository.deleteById(userID);
    }


}
