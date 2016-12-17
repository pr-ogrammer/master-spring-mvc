package pl.prutkowski.master.spring.mvc.controller.api;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pl.prutkowski.master.spring.mvc.MasterSpringMvcApplication;
import pl.prutkowski.master.spring.mvc.domain.User;
import pl.prutkowski.master.spring.mvc.repository.UserRepository;

import java.util.Base64;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by programmer on 12/17/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MasterSpringMvcApplication.class)
@WebAppConfiguration
public class UserApiControllerAuthTest {

    @Autowired
    private FilterChainProxy springSecurityFilter;
    @Autowired
    private WebApplicationContext wac;
    @Autowired
    private UserRepository userRepository;
    private MockMvc mockMvc;

    @Before
    public void setup() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .addFilters(springSecurityFilter).build();

        User user = new User();
        user.setEmail("test@test.com");
        userRepository.reset(user);
    }

    @Test
    public void unauthenticatedCannotListUsers() throws Exception {
        mockMvc.perform(
                get("/api/users")
                        .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isUnauthorized());
    }

    @Test
    public void adminCanListUsers() throws Exception {
        mockMvc.perform(
                get("/api/users")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", basicAuth("admin", "admin"))
        )
        .andExpect(status().isOk());
    }

    private String basicAuth(String login, String password) {
        byte[] auth = (login + ":" + password).getBytes();
        return "Basic " + Base64.getEncoder().encodeToString(auth);
    }
}
