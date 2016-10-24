package pl.prutkowski.master.spring.mvc.controller.search.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.prutkowski.master.spring.mvc.controller.search.SearchService;

import java.util.List;

/**
 * Created by programmer on 10/24/16.
 */
@RestController
@RequestMapping("/api/search")
public class SearchApiController {

    private SearchService searchService;

    @Autowired
    public SearchApiController(SearchService searchService) {
        this.searchService = searchService;
    }

    @RequestMapping(value = "/{searchType}", method = RequestMethod.GET)
    public List<LightTweet> search(@PathVariable String searchType, @MatrixVariable List<String> keywords) {
        return searchService.searchLight(searchType, keywords);
    }
}
