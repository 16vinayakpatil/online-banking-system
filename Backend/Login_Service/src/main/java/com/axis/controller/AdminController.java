package com.axis.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.axis.entity.AuthRequest;
import com.axis.entity.Users;
import com.axis.service.UserDetailsServiceImpl;
import com.axis.util.JwtUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
//@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequestMapping("/login/admin")
public class AdminController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsServiceImpl service;

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

    @GetMapping("/home")
    public String home(HttpServletRequest request) {
        String username = request.getAttribute("username").toString();
        LOGGER.info("Request received for home endpoint by user: {}", username);
        return "Hello " + username + "(admin) welcome home.\nYou're logged in.";
    }

    @PostMapping("/authenticate")
    public String authenticate(@RequestBody AuthRequest authRequest) throws Exception {
        LOGGER.info("Authentication request received by admin for user: {}", authRequest.getUsername());
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            LOGGER.info("Authentication successful for user: {}", authRequest.getUsername());
        } catch (Exception ex) {
            LOGGER.error("Authentication failed for user: {}", authRequest.getUsername());
            throw new Exception("Invalid username/password.");
        }
        return jwtUtil.generateToken(authRequest.getUsername());
    }

    @PostMapping("/register-employee")
    public Users create(@RequestBody Users user) {
        Users createdEmployee = service.createEmployee(user);
        LOGGER.info("Employee {} registered by admin.", createdEmployee.getUsername());
        return createdEmployee;
    }

    @DeleteMapping("/delete-employee/{userid}")
    public String deleteEmployee(@PathVariable int userid) {
       service.deleteEmployee(userid);
        LOGGER.info("Employee {} deleted by admin.");
        return "Employee is deleted successfully";
    }

    @GetMapping("/profile")
    public String profile(HttpServletRequest request) {
        String username = request.getAttribute("username").toString();
        LOGGER.info("Profile request received by admin for user: {}", username);
        Users user = service.viewProfile(username);
        return user.toString();
    }

    @GetMapping("/all-employees")
    public List<Users> allEmployees() {
        LOGGER.info("All employees list retrieved by admin.");
        return service.listAllEmployees();
    }
}
