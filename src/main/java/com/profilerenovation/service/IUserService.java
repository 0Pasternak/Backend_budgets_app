package com.profilerenovation.service;

import java.util.List;

import com.profilerenovation.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface IUserService {

    User registerUser(User user);

    List<User> getUsers();

    void deleteUser(String email);

    User getUser(String email);

}
