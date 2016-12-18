package pl.prutkowski.master.spring.mvc.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import pl.prutkowski.master.spring.mvc.MasterSpringMvcApplication
import pl.prutkowski.master.spring.mvc.controller.search.StubTweeterSearchConfig
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
/**
 * Created by programmer on 12/18/16.
 */
@SpringBootTest(
        classes = [MasterSpringMvcApplication, StubTweeterSearchConfig],
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ContextConfiguration
class HomeControllerSpec extends Specification {

    @Autowired
    WebApplicationContext wac;
    MockMvc mockMvc;

    def setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    def "User redirection to profile page"() {
        when: "open home page"
        def response = mockMvc.perform(get("/"))
        then: "redirect to profile page"
        response
            .andExpect(status().isFound())
            .andExpect(redirectedUrl("/profile"))
    }

    def "User redirection to tastes"() {
        when: "open home page with session"
        def session = new SessionBuilder().userTastes("spring", "scala").build()
        def response = mockMvc.perform(get("/").session(session))
        then: "redirect to tastes"
        response
            .andExpect(status().isFound())
            .andExpect(redirectedUrl("/search/mixed;keywords=spring,scala"))
    }
}
