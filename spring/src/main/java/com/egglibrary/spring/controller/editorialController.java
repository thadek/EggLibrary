package com.egglibrary.spring.controller;


import com.egglibrary.spring.entity.Cliente;
import com.egglibrary.spring.entity.Editorial;
import com.egglibrary.spring.service.EditorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/gestion/editorial")
public class editorialController {

    @Autowired
    private EditorialService editorialService = new EditorialService();

    @GetMapping
    public ModelAndView listar(){
        ModelAndView mav = new ModelAndView("editorial-lista");
        mav.addObject("editorial",editorialService.buscarTodos());
        mav.addObject("title", "Menu Gestión: Editorial");
        return mav;

    }

    @GetMapping("/editar/{id}")
    public ModelAndView editarEditorial(@PathVariable String id){
        ModelAndView mav = new ModelAndView("editorial-formulario");
        mav.addObject("editorial",editorialService.buscarporID(id));
        mav.addObject("title","Editar Editorial");
        mav.addObject("action","modificar");
        return mav;
    }

    @GetMapping("/crear")
    public ModelAndView crearEditorial() {
        ModelAndView mav = new ModelAndView("editorial-formulario");
        mav.addObject("editorial", new Editorial());
        mav.addObject("title", "Crear Editorial");
        mav.addObject("action", "guardar");
        return mav;
    }

    @PostMapping("/eliminar/{id}")
    public RedirectView eliminar(@PathVariable String id){
        try{
            editorialService.eliminar(id);
        }catch(Exception e){
            return new RedirectView("/gestion/editorial?error=eliminar");
        }
         
        return new RedirectView("/gestion/editorial");
    }

    @PostMapping("/modificar")
    public RedirectView modificar(@RequestParam String id, @RequestParam String nombre){
        editorialService.modificar(nombre,id);
        return new RedirectView("/gestion/editorial");
    }


    @PostMapping("/guardar")
    public RedirectView guardar(@RequestParam String nombre){
        editorialService.crear(nombre);
        return new RedirectView("/gestion/editorial");
    }

}
