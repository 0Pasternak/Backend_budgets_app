package com.profilerenovation.security.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.profilerenovation.entity.User;
import com.profilerenovation.repository.UserRepository;

@Service
public class ProfileUserDetailsService implements UserDetailsService {

    int count = 0;

    private final UserRepository userRepository;

    @Autowired
    public ProfileUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        // Agregar registros de depuración
        count++;
        System.out.println("voy por la vuelta de llamada numero: " + count);
        System.out.println("User found: " + user.getEmail());
        System.out.println("User role: " + user.getRole());

        return ProfileUserDetails.buildUserDetails(user);
    }

}
