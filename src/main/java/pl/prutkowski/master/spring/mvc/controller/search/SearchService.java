package pl.prutkowski.master.spring.mvc.controller.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.social.twitter.api.SearchParameters;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Service;
import pl.prutkowski.master.spring.mvc.controller.search.api.LightTweet;
import pl.prutkowski.master.spring.mvc.controller.search.cache.SearchCache;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by programmer on 10/21/16.
 */
@Service
@Profile("!async")
public class SearchService implements TweeterSearch {

    private SearchCache searchCache;

    @Autowired
    public SearchService(SearchCache searchCache) {
        this.searchCache = searchCache;
    }

    @Override
    public List<Tweet> search(String searchType, List<String> keywords) {
        return keywords.stream()
                .flatMap(keyword -> searchCache.fetch(searchType, keywords).stream())
                .collect(Collectors.toList());
    }

    @Override
    public List<LightTweet> searchLight(String searchType, List<String> keywords) {
        return keywords.stream()
                .flatMap(keyword -> searchCache.fetchLight(searchType, keyword).stream())
                .collect(Collectors.toList());
    }
}
