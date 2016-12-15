package pl.prutkowski.master.spring.mvc.controller.search;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.TwitterProfile;
import pl.prutkowski.master.spring.mvc.controller.search.api.LightTweet;

import java.util.Arrays;
import java.util.List;

/**
 * Created by programmer on 12/15/16.
 */
@Configuration
public class StubTweeterSearchConfig {

    @Primary
    @Bean
    public TweeterSearch tweeterSearch() {
        return new TweeterSearch() {
            @Override
            public List<Tweet> search(String searchType, List<String> keywords) {
                return Arrays.asList(
                        createTweet("Text of first tweet"),
                        createTweet("Text of second tweet")
                );
            }

            @Override
            public List<LightTweet> searchLight(String searchType, List<String> keywords) {
                return Arrays.asList(
                        new LightTweet("Text of first tweet"),
                        new LightTweet("Text of second tweet")
                );
            }
        };
    }

    public Tweet createTweet(String text) {
        Tweet tweet = new Tweet(null, text, null, null, null, null, 1, null, null);
        TwitterProfile profile = new TwitterProfile(1, null, null, null, null, null, null, null);
        tweet.setUser(profile);
        return tweet;
    }
}
