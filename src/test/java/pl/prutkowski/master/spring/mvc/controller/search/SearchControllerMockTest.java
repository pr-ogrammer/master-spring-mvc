package pl.prutkowski.master.spring.mvc.controller.search;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.prutkowski.master.spring.mvc.MasterSpringMvcApplication;

import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by programmer on 12/15/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MasterSpringMvcApplication.class)
@WebAppConfiguration
public class SearchControllerMockTest {

    @Mock
    private TweeterSearch searchService;
    @InjectMocks
    private SearchController searchController;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(searchController)
                .setRemoveSemicolonContent(false)
                .build();
    }

    @Test
    public void shouldSearch() throws Exception {
        when(searchService.search(anyString(), anyListOf(String.class)))
                .thenReturn(Arrays.asList(new Tweet("1", "Text of tweet", null, null, null, null, 1, null, null)));

        mockMvc.perform(get("/search/mixed;keywords=spring"))
                .andExpect(status().isOk())
                .andExpect(view().name("resultPageTweets"))
                .andExpect(model().attribute("tweets", everyItem(
                        hasProperty("text", is("Text of tweet"))
                )));

        verify(searchService, times(1)).search(anyString(), anyListOf(String.class));
    }
}
