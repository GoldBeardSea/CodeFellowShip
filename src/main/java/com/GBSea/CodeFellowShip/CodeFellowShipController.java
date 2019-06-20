package com.GBSea.CodeFellowShip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.Date;


@Controller
public class CodeFellowShipController {

    @Autowired
    PostRepository postRepository;
    @Autowired
    AppUserRepository appUserRepository;


    @GetMapping("/")
    public String getController(Principal p, Model m) {
        System.out.println(p.getName());
        AppUser loggeduser = appUserRepository.findByUsername(p.getName());

        m.addAttribute("loggeduser", loggeduser);
        return "codefellowship";
    }

    @GetMapping("/post/create")
    public String getPostCreation(Principal p, Model m) {
        m.addAttribute("principal", p);

        return "createPost";
    }

    @GetMapping("/feed")
    public String getFeed(Principal p, Model m) {
        m.addAttribute("principal", p);

        return "feed";
    }


    @PostMapping("/post")
    public RedirectView createPost(String subject, String body, Principal p) {
        Post post = new Post();
        post.subject = subject;
        post.body = body;
        post.createdOn = new Date();
        AppUser me = appUserRepository.findByUsername(p.getName());
        post.user = me;
        postRepository.save(post);
        return new RedirectView("/");
    }

    @GetMapping("/posts/{id}")
    public String showPost(@PathVariable long id, Model m, Principal p) {
        Post post = postRepository.findById(id).get();
        if (post.getUser().username.equals(p.getName())) {
            m.addAttribute("post", post);
            return "post";
        } else {
            throw new PostUserNotMatchedException("That post does not belong to you.");
        }


    }
}

// came from https://stackoverflow.com/questions/2066946/trigger-404-in-spring-mvc-controller
@ResponseStatus(value = HttpStatus.FORBIDDEN)
class PostUserNotMatchedException extends RuntimeException {
    public PostUserNotMatchedException(String s) {
        super(s);
    }
}