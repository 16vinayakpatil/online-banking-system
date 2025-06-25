package com.axis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.axis.entity.Roles;
import com.axis.entity.Users;
import com.axis.repository.RoleRepository;
import com.axis.repository.UserRepository;
@EnableDiscoveryClient
@SpringBootApplication
public class LoginServiceApplication implements CommandLineRunner{	
	
	@Autowired
	private UserRepository ur;
	
	@Autowired
	private RoleRepository rr;
	
	public static void main(String[] args) {
		SpringApplication.run(LoginServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Users adminUser = ur.findByUsername("admin");
        if (adminUser == null) {
            adminUser = new Users();
            adminUser.setUsername("admin");
            adminUser.setPassword("admin"); 
            adminUser.setEmail("admin@gmail.com");
            adminUser.setAddress("Nowhere");
            adminUser.setPhone("**********");
            ur.save(adminUser);
            rr.save(new Roles("ROLE_ADMIN",adminUser.getId()));
        }
	}
	
	}

