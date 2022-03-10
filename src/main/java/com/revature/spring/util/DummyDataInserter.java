package com.revature.spring.util;

import com.revature.spring.models.User;
import com.revature.spring.models.UserRole;
import com.revature.spring.repos.UserRepository;
import com.revature.spring.repos.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DummyDataInserter implements CommandLineRunner {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    @Autowired
    public DummyDataInserter(UserRepository userRepository, UserRoleRepository userRoleRepository){
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        User user1 = new User();
        user1.setId(UUID.randomUUID().toString());
        user1.setGiven_name("Guy");
        user1.setSurname("Dood");
        user1.setEmail("guydood@gmail.com");
        user1.setUsername("HandsomeDevil");
        user1.setPassword("p4$$WORD");
        user1.setActive(true);
        user1.setRole(new UserRole("1", "ADMIN"));

        User user2 = new User();
        user2.setId(UUID.randomUUID().toString());
        user2.setGiven_name("Lady");
        user2.setSurname("Gal");
        user2.setEmail("ladygal@gmail.com");
        user2.setUsername("WonderWoman");
        user2.setPassword("p4$$WORD");
        user2.setActive(true);
        user2.setRole(new UserRole("2", "USER"));

        UserRole admin = new UserRole();
        admin.setId("1");
        admin.setRole("ADMIN");

        UserRole user = new UserRole();
        user.setId("2");
        user.setRole("USER");

        UserRole manager = new UserRole();
        manager.setId("3");
        manager.setRole("MANAGER");

        userRoleRepository.save(admin);
        userRoleRepository.save(user);
        userRoleRepository.save(manager);
        userRepository.save(user1);
        userRepository.save(user2);

        System.out.println(userRepository.getUserByUsername("iamwolverine"));
    }
}
