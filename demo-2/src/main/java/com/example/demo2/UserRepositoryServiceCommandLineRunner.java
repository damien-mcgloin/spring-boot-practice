package com.example.demo2;

import com.example.demo2.entity.User;
import com.example.demo2.service.UserDAOService;
import com.example.demo2.service.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserRepositoryServiceCommandLineRunner implements CommandLineRunner {

    private static final Logger log =
            LoggerFactory.getLogger(UserRepositoryServiceCommandLineRunner.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        User user = new User("Jason", "Admin");
        userRepository.save(user);

        log.info("New user is created "+user);
        Optional<User> userWithIdOne = userRepository.findById(1L);
        log.info("User is retrieved : "+userWithIdOne);

        List<User> users = userRepository.findAll();
        log.info("All Users : " + users);

    }
    
}
