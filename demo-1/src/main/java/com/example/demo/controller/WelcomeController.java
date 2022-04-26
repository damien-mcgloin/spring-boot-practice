package com.example.demo.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
public class WelcomeController {

    /*
    @RequestMapping(value = "/login", method=RequestMethod.POST)
    public String showWelcomePage(ModelMap model)
    {
        model.addAttribute("name", getLoggedinUserName());
        return "welcome";
    }

    private String getLoggedinUserName(){

        Object principal = SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        if (principal instanceof UserDetails){
            return ((UserDetails) principal).getUsername();
        }

        return principal.toString();
    }

     */

    /*
    @RequestMapping(value = "/login", method=RequestMethod.POST)
    public String showWelcomePage(@RequestParam String name, @RequestParam String password, Model model)
    {
        boolean isValidUser = loginService.validateUser(name, password);
        if (!isValidUser) {
            model.addAttribute("errorMessage", "Invalid Credentials");
            return "login";
        }

        model.addAttribute("name", name);
        model.addAttribute("password", password);
        return "welcome";
    }
     */

}
