package pl.prutkowski.master.spring.mvc.controller.profile;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.prutkowski.master.spring.mvc.date.USLocalDateFormatter;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Locale;

/**
 * Created by programmer on 10/9/16.
 */
@Controller
public class ProfileController {

    private static final String PROFILE_FORM_VIEW = "profile/profilePage";

    @ModelAttribute("dateFormat")
    public String localeFormat(Locale locale) {
        return USLocalDateFormatter.getPattern(locale);
    }

    @GetMapping("/profile")
    public String displayProfile(ProfileForm profileForm) {
        return PROFILE_FORM_VIEW;
    }

    @PostMapping(value = "/profile", params = {"save"})
    public String saveProfile(@Valid ProfileForm profileForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return PROFILE_FORM_VIEW;
        }
        return "redirect:/profile";
    }

    @PostMapping(value = "/profile", params = {"addTaste"})
    public String addRow(@Valid ProfileForm profileForm, BindingResult bindingResult) {
        profileForm.getTastes().add(null);
        return PROFILE_FORM_VIEW;
    }

    @PostMapping(value = "/profile", params = {"removeTaste"})
    public String removeRow(@Valid ProfileForm profileForm, BindingResult bindingResult, HttpServletRequest request) {
        Integer rowId = Integer.valueOf(request.getParameter("removeTaste"));
        profileForm.getTastes().remove(rowId.intValue());
        return PROFILE_FORM_VIEW;
    }
}
