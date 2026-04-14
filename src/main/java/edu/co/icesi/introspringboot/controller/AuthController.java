package edu.co.icesi.introspringboot.controller;


import edu.co.icesi.introspringboot.entity.User;
import edu.co.icesi.introspringboot.security.AppUser;
import edu.co.icesi.introspringboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login(Model model) {
        return "auth/login";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        //APortar un usuario vacio (skeleton)
        model.addAttribute("user", new User());
        return "auth/signup";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute User user) {
        userService.save(user);
        return "redirect:/auth/login";
    }

    //My Profile
    @GetMapping("/profile")
    public String profile(Model model, Authentication authentication) {
        AppUser user = (AppUser) authentication.getPrincipal();
        model.addAttribute("user", user.getUsername());
        model.addAttribute("auths", user.getAuthorities());
        return "auth/profile";
    }


}
