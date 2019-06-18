package com.GBSea.CodeFellowShip;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;


@Controller
public class CodeFellowShipController {


    @GetMapping("/")
    public String getController(Principal p, Model m) {
        System.out.println(p.getName());
        m.addAttribute("principal", p);
        return "codefellowship";
    }
}