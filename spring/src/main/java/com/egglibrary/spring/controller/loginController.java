package com.egglibrary.spring.controller;

import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/login")
public class loginController {
    
    
@GetMapping
public ModelAndView vista(@RequestParam(required=false) String error, @RequestParam (required=false) String logout, HttpSession sesion ){
    ModelAndView mav = new ModelAndView("login");
    if(error!=null){
        sesion.removeAttribute("usuariosession");
        mav.addObject("error", "Nombre de usuario o contraseña incorrectos.");
    }
    
    if(logout!=null){
        mav.addObject("logout", "Se cerró correctamente la sesión.");
    }
    
    
    return mav;
}
    
    
}
