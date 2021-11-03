package com.file.upload.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.file.upload.image.ImageService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserRepository userRepository;

    @MockBean
    UserService userService;

    @MockBean
    ImageService imageService;



    User user_1;
    User user_2;
    User user_3;
    User user_4;

    @BeforeEach
    void setUp() {
        user_1 = new User();
        user_1.setName("Auga");
        user_1.setFirstName("Augustine");
        user_1.setLastName("Simwela");

        user_2 = new User();
        user_2.setName("Salo");
        user_2.setFirstName("Salome");
        user_2.setLastName("Simwela");

        user_3 = new User();
        user_3.setName("Jozy");
        user_3.setFirstName("Francis");
        user_3.setLastName("Simwela");

        user_4= new User();
        user_4.setName("Nel");
        user_4.setFirstName("Nelson");
        user_4.setLastName("Simwela");

        userRepository.saveAll(Stream.of(user_1, user_2, user_3, user_4).collect(Collectors.toList()));
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void getAllUsers() throws Exception {
        List<User> users = new ArrayList<>(Arrays.asList(user_1, user_2, user_3, user_4));

//        Mockito.when(userService.getPagedUsers(1,20, null)).thenReturn(users);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andDo(print());
               // .andExpect(content().json("$._embedded.users"));

               // .andExpect(jsonPath());
//                .andExpect(jsonPath("$._embedded.users[2].name", is("Jozy")));

    }

    @Test
    void should_return_user_list() throws Exception {
        List<User> users = new ArrayList<>(Arrays.asList(user_1, user_2, user_3, user_4));

        Mockito.when(userService.getUserList()).thenReturn(users);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/users/list")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                 .andExpect(jsonPath("$", hasSize(4)));

        // .andExpect(jsonPath());
//                .andExpect(jsonPath("$._embedded.users[2].name", is("Jozy")));

    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}