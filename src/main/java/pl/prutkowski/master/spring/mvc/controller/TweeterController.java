package pl.prutkowski.master.spring.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.SearchResults;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by programmer on 10/6/16.
 */
@Controller
public class TweeterController {

    private static final String viewName = "resultPageTweets";

    @Autowired
    private Twitter twitter;

    @RequestMapping("/getTweets/")
    public String getTweet(@RequestParam(defaultValue = "Spring Boot") String search, Model model) {
        SearchResults searchResult = twitter.searchOperations().search(search);
        List<String> tweets = searchResult.getTweets()
                .stream()
                .map(Tweet::getText)
                .collect(Collectors.toList());
        model.addAttribute("tweets", tweets);
        return viewName;
    }
}
