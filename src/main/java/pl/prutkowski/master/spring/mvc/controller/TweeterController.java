package pl.prutkowski.master.spring.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.SearchResults;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by programmer on 10/6/16.
 */
@Controller
public class TweeterController {

    private static final String SEARCH_VIEW_NAME = "searchPageTweets";
    private static final String RESULT_VIEW_NAME = "resultPageTweets";

    @Autowired
    private Twitter twitter;

    @RequestMapping("/searchTweet")
    public String searchTweets() {
        return SEARCH_VIEW_NAME;
    }

    @PostMapping("/postSearch")
    public String postSearchTweets(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String search = request.getParameter("search");
        if ("rubbish".equals(search.toLowerCase())) {
            redirectAttributes.addFlashAttribute("error", "Try again, sorry!");
            return "redirect:/";
        }
        redirectAttributes.addAttribute("search", search);
        return "redirect:result";
    }

    @RequestMapping("/result")
    public String getTweet(@RequestParam(defaultValue = "Spring Boot") String search, Model model) {
        SearchResults searchResult = twitter.searchOperations().search(search);
        model.addAttribute("tweets", searchResult.getTweets());
        model.addAttribute("search", search);
        return RESULT_VIEW_NAME;
    }
}
