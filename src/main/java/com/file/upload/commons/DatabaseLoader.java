package com.file.upload.commons;

import com.file.upload.user.User;
import com.file.upload.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DatabaseLoader {
   @Bean
    CommandLineRunner init(UserRepository userRepository){
       return args -> {

           User usr  = new User();
           usr.setName("Auga");
           usr.setFirstName("Augustine");
           usr.setLastName("Simwela");

           User usr1  = new User();
           usr1.setName("Vic");
           usr1.setFirstName("Victoria");
           usr1.setLastName("Simwela");

           User usr2  = new User();
           usr2.setName("Salo");
           usr2.setFirstName("Salome");
           usr2.setLastName("Simwela");

           User usr3  = new User();
           usr3.setName("Nel");
           usr3.setFirstName("Nelson");
           usr3.setLastName("Simwela");

           User usr4  = new User();
           usr4.setName("Jose");
           usr4.setFirstName("Francis");
           usr4.setLastName("Simwela");

           User usr5  = new User();
           usr5.setName("Gib");
           usr5.setFirstName("Gabriel");
           usr5.setLastName("Simwela");

           List<User> users = new ArrayList<>();

           users.add(usr);
           users.add(usr1);
           users.add(usr2);
           users.add(usr3);
           users.add(usr4);
           users.add(usr5);

           userRepository.saveAll(users);
       };
   }
}
