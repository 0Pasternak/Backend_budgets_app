package com.profilerenovation.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.profilerenovation.dto.UserDTO;
import com.profilerenovation.entity.User;
import com.profilerenovation.exceptions.UserAlreadyExistsException;
import com.profilerenovation.request.LoginRequest;
import com.profilerenovation.response.JwtResponse;
import com.profilerenovation.security.jwt.JwtUtils;
import com.profilerenovation.security.user.ProfileUserDetails;
import com.profilerenovation.service.IUserService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/auth")
@RestController
public class AuthController {

    private final IUserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Autowired
    public AuthController(IUserService userService, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/register-user")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDTO userDto) {
        try {
            User user = new User();
            user.setUserName(userDto.getUserName());
            user.setEmail(userDto.getEmail());
            user.setPassword(userDto.getPassword()); // La contraseña debe estar codificada en el servicio
            user.setRole(userDto.getRole());

            userService.registerUser(user);
            return ResponseEntity.ok("Registration successful");
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during registration");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest request) {
        // Imprimir los datos del request para depuración
        System.out.println("Recibido login request:");
        System.out.println("Email: " + request.getEmail());
        System.out.println("Password: " + request.getPassword());

        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtTokenForUser(authentication);
            ProfileUserDetails userDetails = (ProfileUserDetails) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
            JwtResponse jwtResponse = new JwtResponse(userDetails.getId(), userDetails.getEmail(), jwt, roles);

            // Imprimir los detalles del usuario autenticado
            System.out.println("Usuario autenticado:");
            System.out.println("ID: " + userDetails.getId());
            System.out.println("Email: " + userDetails.getEmail());
            System.out.println("Password: " + userDetails.getPassword());

            System.out.println("Roles: " + roles);

            System.out.println("Login correcto");
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(jwtResponse);
        } catch (UsernameNotFoundException e) {
            System.out.println("Error: Usuario no encontrado");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        } catch (Exception e) {
            System.out.println("Error durante la autenticación: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during authentication");
        }
    }

}
