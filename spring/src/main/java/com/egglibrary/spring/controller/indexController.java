package com.egglibrary.spring.controller;

import com.egglibrary.spring.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/")
public class indexController {

    
    @Autowired
    UsuarioService us = new UsuarioService();
    
    @GetMapping()
    public ModelAndView inicio() {
        return new ModelAndView("index");
    }

@GetMapping("/register")
    public ModelAndView registrarse(){
        return new ModelAndView("register");
}

    
 @PostMapping("/registrarUsuario")
 public RedirectView guardarUser(@RequestParam("username") String username, @RequestParam("email") String email, @RequestParam String password1 , @RequestParam String password2){
     try{
         us.registrar(username, password1, password2, email);
     }catch(Exception e){
         return new RedirectView("/register?error");
     }
     return new RedirectView("/register?ok");
     
 }

}
