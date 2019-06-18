package com.GBSea.CodeFellowShip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;

@Controller
public class AppUserController {
    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    PasswordEncoder bCryptPasswordEncoder;


    @PostMapping("/users")
    public RedirectView createUser(String username, String password) {
        AppUser newUser = new AppUser(username, bCryptPasswordEncoder.encode(password));
        appUserRepository.save(newUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(newUser, null, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new RedirectView("/");
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/signup")
    public RedirectView addGreeting(@RequestParam String title, @RequestParam String artist, @RequestParam int songCount, @RequestParam int length, @RequestParam String imageUrl) {
        AppUser album = new AppUser(title, artist, songCount, length, imageUrl);
        appUserRepository.save(album);
        return new RedirectView("/albums");
    }

}