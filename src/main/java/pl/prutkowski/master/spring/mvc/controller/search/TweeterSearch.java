package pl.prutkowski.master.spring.mvc.controller.search;

import org.springframework.social.twitter.api.Tweet;
import pl.prutkowski.master.spring.mvc.controller.search.api.LightTweet;

import java.util.List;

/**
 * Created by programmer on 12/15/16.
 */
public interface TweeterSearch {

    List<Tweet> search(String searchType, List<String> keywords);

    List<LightTweet> searchLight(String searchType, List<String> keywords);
}
