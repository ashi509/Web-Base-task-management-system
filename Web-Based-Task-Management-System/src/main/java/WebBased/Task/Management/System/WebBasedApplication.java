package WebBased.Task.Management.System;

import WebBased.Task.Management.System.entity.Users;
import WebBased.Task.Management.System.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.Date;

@SpringBootApplication
public class WebBasedApplication {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserRepository userRepository;
    public static void main(String[] args) {
        SpringApplication.run(WebBasedApplication.class,args);
    }

//    @PostConstruct
    public void init(){
        Users users=new Users(0,"Ashish","ashishkumar3110199@gmail.com","SUPER_ADMIN", bCryptPasswordEncoder.encode("shiv"),
                new Date());
        userRepository.save(users);
        System.out.println(users);
    }
}
