package com.GBSea.CodeFellowShip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.ArrayList;

@Controller
public class AppUserController {
    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    PasswordEncoder bCryptPasswordEncoder;


//    @PostMapping("/login")
//    public RedirectView createUser(String username, String password) {
//        AppUser newUser = new AppUser(username, bCryptPasswordEncoder.encode(password));
//        appUserRepository.save(newUser);
//        Authentication authentication = new UsernamePasswordAuthenticationToken(newUser, null, new ArrayList<>());
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        return new RedirectView("/");
//    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/signup")
    public String getSignup() {return "appuserform";}

    @PostMapping("/signup")
    public RedirectView addUser(@RequestParam String username, @RequestParam String password, @RequestParam String firstName, @RequestParam String lastName, @RequestParam String dateOfBirth, @RequestParam String bio) {
        AppUser user = new AppUser(username, bCryptPasswordEncoder.encode(password), firstName, lastName, dateOfBirth, bio);
        appUserRepository.save(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new RedirectView("/myprofile");
    }

    @GetMapping("/users/{id}")
    public String showUser(@PathVariable long id, Model m, Principal p) {
        AppUser user = appUserRepository.findById(id).get();
        m.addAttribute("user", user);
        return "user";
    }

    @GetMapping("/myprofile")
    public String getMyProfile(Principal p, Model m) {
        AppUser user = appUserRepository.findByUsername(p.getName());
        m.addAttribute("principal", p);
        m.addAttribute("user", user);
        return "myProfile";
    }
}