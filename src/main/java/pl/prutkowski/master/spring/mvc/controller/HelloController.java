package pl.prutkowski.master.spring.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by programmer on 10/4/16.
 */
@Controller
public class HelloController {

    private static final String viewName = "resultPage";

    @RequestMapping("/")
    public String hello(@RequestParam(defaultValue = "anonymous") String name, Model model) {
        model.addAttribute("message", String.format("Hello %s! This message is from HelloController!", name));
        return viewName;
    }
}
