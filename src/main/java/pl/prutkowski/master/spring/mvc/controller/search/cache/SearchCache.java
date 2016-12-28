package pl.prutkowski.master.spring.mvc.controller.search.cache;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.social.TwitterProperties;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.social.twitter.api.SearchParameters;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.stereotype.Service;
import pl.prutkowski.master.spring.mvc.controller.search.SearchParamsBuilder;
import pl.prutkowski.master.spring.mvc.controller.search.api.LightTweet;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by programmer on 12/28/16.
 */
@Service
public class SearchCache {

    protected final Log LOGGER = LogFactory.getLog(getClass());
    private Twitter twitter;

    @Autowired
    public SearchCache(TwitterProperties twitterProperties) {
        this.twitter = new TwitterTemplate(twitterProperties.getAppId(), twitterProperties.getAppSecret());
    }

    @Cacheable("searches")
    public List<LightTweet> fetchLight(String searchType, String keyword) {
        LOGGER.info("Word " + keyword + " doesn't exist in cache");
        SearchParameters searchParam = SearchParamsBuilder.createSearchParam(searchType, keyword);
        return twitter.searchOperations()
                .search(searchParam)
                .getTweets().stream()
                .map(LightTweet::ofTweet)
                .collect(Collectors.toList());
    }

    @Cacheable("searches")
    public List<Tweet> fetch(String searchType, List<String> keywords) {
        LOGGER.info("Words " + keywords + " don't exist in cache");
        List<SearchParameters> searches = keywords.stream()
                .map(taste -> SearchParamsBuilder.createSearchParam(searchType, taste))
                .collect(Collectors.toList());
        List<Tweet> tweets = searches.stream()
                .map(params -> twitter.searchOperations().search(params))
                .flatMap(searchResults -> searchResults.getTweets().stream())
                .collect(Collectors.toList());
        return tweets;
    }
}
