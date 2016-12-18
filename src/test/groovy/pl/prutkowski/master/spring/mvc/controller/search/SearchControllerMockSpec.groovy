package pl.prutkowski.master.spring.mvc.controller.search

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.social.twitter.api.Tweet
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import pl.prutkowski.master.spring.mvc.MasterSpringMvcApplication
import spock.lang.Specification

import static org.hamcrest.Matchers.*
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view

/**
 * Created by programmer on 12/18/16.
 */
@SpringBootTest(
        classes = MasterSpringMvcApplication,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ContextConfiguration
class SearchControllerMockSpec extends Specification {

    def twitterSearch = Mock(TweeterSearch)
    def searchController = new SearchController(twitterSearch)
    def mockMvc = MockMvcBuilders.standaloneSetup(searchController)
        .setRemoveSemicolonContent(false)
        .build()

    def "search result"() {
        when: "seraching word spring"
        def response = mockMvc.perform(get("/search/mixed;keywords=spring"))
        then:
        1 * twitterSearch.search(_, _) >> [new Tweet("1", "Text of tweet", null, null, null, null, 1, null, null)]
        and:
        response
            .andExpect(status().isOk())
            .andExpect(view().name("resultPageTweets"))
        and:
        response
            .andExpect(model().attribute("tweets", everyItem(
                hasProperty("text", is("Text of tweet"))
        )))
    }
}
