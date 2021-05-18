
package com.egglibrary.spring.controller;

import com.egglibrary.spring.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/gestion")
public class gestionController{

    
    @Autowired
    private ClienteService clienteService = new ClienteService();
    
    @GetMapping
    public ModelAndView gestion() {
        return new ModelAndView("gestion");
    }
    
   

    
    
    
    
    
}