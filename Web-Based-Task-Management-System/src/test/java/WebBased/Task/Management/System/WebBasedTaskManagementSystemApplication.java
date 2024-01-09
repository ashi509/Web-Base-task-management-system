package WebBased.Task.Management.System;

import WebBased.Task.Management.System.entity.Users;
import WebBased.Task.Management.System.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class WebBasedTaskManagementSystemApplication {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public static void main(String[] args) {
		SpringApplication.run( WebBasedTaskManagementSystemApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@PostConstruct
	public void insertUser(){
		List<Users> users=new ArrayList<>();
		users.add(new Users(0,"Ashish","ashish@gmail.com","SUPER_ADMIN", bCryptPasswordEncoder().encode("Ashish"),new Date()));
		System.out.println(userRepository.saveAll(users));
	}
}
