
package com.egglibrary.spring.controller;

import com.egglibrary.spring.entity.Cliente;
import com.egglibrary.spring.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;



@Controller
@RequestMapping("/gestion/clientes")
public class clientesController{


    @Autowired
    private ClienteService clienteService = new ClienteService();
    
       
    @GetMapping
    public ModelAndView mostrarTodos() {
        ModelAndView mav = new ModelAndView("cliente-lista");
        mav.addObject("cliente", clienteService.buscarTodos());
        mav.addObject("title", "Menu Gestión: Cliente");
        return mav;
    }

   @GetMapping("/editar/{documento}")
    public ModelAndView editarCliente(@PathVariable Long documento) {
        ModelAndView mav = new ModelAndView("cliente-formulario");
        mav.addObject("cliente", clienteService.buscarPorDni(documento));
        mav.addObject("title", "Editar Cliente");
        mav.addObject("action", "modificar");
        return mav;
    }
    
    @PostMapping("/modificar")
    public RedirectView modificar(@RequestParam Long documento, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String telefono, @RequestParam String domicilio){
        clienteService.modificar(documento, nombre, apellido, domicilio, telefono);
        return new RedirectView("/gestion/clientes");
    }
    
    @PostMapping ("/eliminar/{documento}")
    public RedirectView eliminar(@PathVariable Long documento){
        try{
            clienteService.eliminar(documento);
        }catch(Exception e){
            return new RedirectView("/gestion/clientes?error=eliminar");
        }    
        return new RedirectView("/gestion/clientes");
    }
      
    
    @GetMapping("/crear")
    public ModelAndView crearCliente() {
        ModelAndView mav = new ModelAndView("cliente-formulario");
        mav.addObject("cliente", new Cliente());
        mav.addObject("title", "Crear Cliente");
        mav.addObject("action", "guardar");
        return mav;
    }
    
    @PostMapping("/guardar")
    public RedirectView crear(@RequestParam Long documento, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String telefono, @RequestParam String domicilio){
        clienteService.crear(documento, nombre, apellido, domicilio, telefono);
        return new RedirectView("/gestion/clientes");
    }
}
    
    
    
    
