package com.caban.eazyschool.controller;

import com.caban.eazyschool.model.Person;
import com.caban.eazyschool.model.Profile;
import com.caban.eazyschool.service.ProfileService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/displayProfile")
    public ModelAndView displayProfile(Model model, HttpSession session) {
        Person person = (Person) session.getAttribute("loggedInPerson");
        ModelAndView modelAndView = new ModelAndView("profile");
        modelAndView.addObject("profile", profileService.populateProfileData(person));
        return modelAndView;
    }

    @PostMapping(value = "/updateProfile")
    public String updateProfile(@Valid @ModelAttribute("profile") Profile profile, Errors errors,
                                HttpSession session) {
        if (errors.hasErrors()) {
            return "profile.html";
        }

        Person person = (Person) session.getAttribute("loggedInPerson");

        session.setAttribute("loggedInPerson", profileService.updateProfile(person, profile));

        return "redirect:/displayProfile";

    }
}
