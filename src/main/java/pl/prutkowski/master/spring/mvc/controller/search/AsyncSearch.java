package pl.prutkowski.master.spring.mvc.controller.search;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import pl.prutkowski.master.spring.mvc.controller.search.api.LightTweet;
import pl.prutkowski.master.spring.mvc.controller.search.cache.SearchCache;

import java.util.List;

/**
 * Created by programmer on 12/29/16.
 */
@Component
public class AsyncSearch {

    protected final Log logger = LogFactory.getLog(getClass());
    private SearchCache searchCache;

    @Autowired
    public AsyncSearch(SearchCache searchCache) {
        this.searchCache = searchCache;
    }

    @Async
    public ListenableFuture<List<LightTweet>> asyncFetchLight(String searchType, String keyword) {
        logger.info(Thread.currentThread().getName() + " - searching word " + keyword);
        return new AsyncResult<>(searchCache.fetchLight(searchType, keyword));
    }

    @Async
    public ListenableFuture<List<Tweet>> asyncFetch(String searchType, List<String> keywords) {
        logger.info(Thread.currentThread().getName() + " - searching word " + keywords);
        return new AsyncResult<>(searchCache.fetch(searchType, keywords));
    }
}
