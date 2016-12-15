package pl.prutkowski.master.spring.mvc.controller.search.api;

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
import pl.prutkowski.master.spring.mvc.controller.search.StubTweeterSearchConfig;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by programmer on 12/15/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {MasterSpringMvcApplication.class, StubTweeterSearchConfig.class})
@WebAppConfiguration
public class SearchApiControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void shouldSearch() throws Exception {
        mockMvc.perform(get("/api/search/mixed;keywords=spring").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].text", is("Text of first tweet")))
                .andExpect(jsonPath("$[1].text", is("Text of second tweet")));
    }
}
