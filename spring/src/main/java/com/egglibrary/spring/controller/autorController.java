package com.egglibrary.spring.controller;

import com.egglibrary.spring.entity.Autor;
import com.egglibrary.spring.service.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/gestion/autor")
public class autorController {

    @Autowired
    private AutorService autorService = new AutorService();

    @GetMapping
    public ModelAndView listar(){
        ModelAndView mav = new ModelAndView("autor-lista");
        mav.addObject("autor",autorService.buscarTodos());
        mav.addObject("title", "Menu Gestión: Autor");
        return mav;

    }

    @GetMapping("/editar/{id}")
    public ModelAndView editarAutor(@PathVariable String id){
        ModelAndView mav = new ModelAndView("autor-formulario");
        mav.addObject("autor",autorService.buscarporID(id));
        mav.addObject("title","Editar Autor");
        mav.addObject("action","modificar");
        return mav;
    }

    @GetMapping("/crear")
    public ModelAndView crearAutor() {
        ModelAndView mav = new ModelAndView("autor-formulario");
        mav.addObject("autor", new Autor());
        mav.addObject("title", "Crear Autor");
        mav.addObject("action", "guardar");
        return mav;
    }

    @PostMapping("/eliminar/{id}")
    public RedirectView eliminar(@PathVariable String id){
        
        try{
            autorService.eliminar(id);
        }catch(Exception e){
            return new RedirectView("/gestion/autor?error=eliminar");
        }
        
        return new RedirectView("/gestion/autor");
    }

    @PostMapping("/modificar")
    public RedirectView modificar(@RequestParam String id, @RequestParam String nombre){
        autorService.modificar(nombre,id);
        return new RedirectView("/gestion/autor");
    }


    @PostMapping("/guardar")
    public RedirectView guardar(@RequestParam String nombre){
        autorService.crear(nombre);
        return new RedirectView("/gestion/autor");
    }
}
