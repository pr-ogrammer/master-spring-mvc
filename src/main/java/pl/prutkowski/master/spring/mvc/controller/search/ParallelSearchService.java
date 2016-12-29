package pl.prutkowski.master.spring.mvc.controller.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.stereotype.Service;
import pl.prutkowski.master.spring.mvc.controller.search.api.LightTweet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by programmer on 12/29/16.
 */
@Service
@Profile("async")
public class ParallelSearchService implements TweeterSearch {

    private final AsyncSearch asyncSearch;

    @Autowired
    public ParallelSearchService(AsyncSearch asyncSearch) {
        this.asyncSearch = asyncSearch;
    }

    @Override
    public List<Tweet> search(String searchType, List<String> keywords) {
        return Collections.emptyList();
    }

    @Override
    public List<LightTweet> searchLight(String searchType, List<String> keywords) {
        CountDownLatch latch = new CountDownLatch(keywords.size());
        List<LightTweet> allTweets = Collections.synchronizedList(new ArrayList<>());
        keywords.stream().forEach(keyword -> asyncFetch(latch, allTweets, searchType, keyword));
        await(latch);
        return allTweets;
    }

    private void asyncFetch(CountDownLatch latch, List<LightTweet> allTweets, String searchType, String keyword) {
        asyncSearch
                .asyncFetchLight(searchType, keyword)
                .addCallback(tweets -> onSuccess(allTweets, latch, tweets), ex -> onError(latch, ex));
    }

    private void await(CountDownLatch latch) {
        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }

    private static void onSuccess(List<LightTweet> results, CountDownLatch latch, List<LightTweet> tweets) {
        results.addAll(tweets);
        latch.countDown();
    }

    private static void onError(CountDownLatch latch, Throwable ex) {
        ex.printStackTrace();
        latch.countDown();
    }
}
