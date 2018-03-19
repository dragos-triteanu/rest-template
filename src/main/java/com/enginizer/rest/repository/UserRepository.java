package com.enginizer.rest.repository;

import com.enginizer.rest.model.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {

}
