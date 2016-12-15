package pl.prutkowski.master.spring.mvc.controller.api;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pl.prutkowski.master.spring.mvc.MasterSpringMvcApplication;
import pl.prutkowski.master.spring.mvc.controller.JsonUtil;
import pl.prutkowski.master.spring.mvc.domain.User;
import pl.prutkowski.master.spring.mvc.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by programmer on 12/15/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MasterSpringMvcApplication.class)
@WebAppConfiguration
public class UserApiControllerTest {

    @Autowired
    private WebApplicationContext wac;
    @Autowired
    private UserRepository userRepository;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        User user = new User();
        user.setEmail("test@test.com");
        userRepository.reset(user);
    }

    @Test
    public void shouldListUsers() throws Exception {
        mockMvc.perform(get("/api/users").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].email", is("test@test.com")));
    }

    @Test
    public void shouldCreateNewUser() throws Exception {
        User user = new User();
        user.setEmail("test2@test2.com");
        mockMvc.perform(
                post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(user))
        )
        .andExpect(status().isCreated());

        assertThat(userRepository.findAll())
                .extracting(User::getEmail)
                .containsOnly("test@test.com", "test2@test2.com");
    }

    @Test
    public void shouldDeleteUser() throws Exception {
        mockMvc.perform(
                delete("/api/user/test@test.com")
                        .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk());

        assertThat(userRepository.findAll()).hasSize(0);
    }

    @Test
    public void shouldReturnNotFoundWhenDeletingUnknownUser() throws Exception {
        mockMvc.perform(
                delete("/api/user/not-found@email.com")
                        .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isNotFound());
    }

    @Test
    public void putShouldUpdateExistingUser() throws Exception {
        User user = new User();
        user.setEmail("new-user@email.com");
        user.setTwitterHandle("TwitterHandle");

        mockMvc.perform(
                put("/api/user/test@test.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(user))
        ).andDo(print())
        .andExpect(status().isCreated());

        assertThat(userRepository.findAll())
                .extracting(User::getEmail)
                .containsOnly("test@test.com");

        assertThat(userRepository.findAll())
                .extracting(User::getTwitterHandle)
                .containsOnly("TwitterHandle");
    }

}
