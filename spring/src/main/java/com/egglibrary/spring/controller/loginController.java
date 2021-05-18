package com.egglibrary.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/login")
public class loginController {
    
    
@GetMapping
public ModelAndView vista(){
    return new ModelAndView("login");
}
    
    
}
