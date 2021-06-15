package com.egglibrary.spring.controller;

import com.egglibrary.spring.entity.Libro;
import com.egglibrary.spring.entity.Prestamo;
import com.egglibrary.spring.errores.ErrorService;
import com.egglibrary.spring.service.ClienteService;
import com.egglibrary.spring.service.LibroService;
import com.egglibrary.spring.service.PrestamoService;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/gestion/prestamo")
public class prestamoController {
    
    @Autowired
    PrestamoService ps = new PrestamoService();
    @Autowired
    LibroService ls = new LibroService();
    @Autowired
    ClienteService cs = new ClienteService();
    
    @GetMapping
    public ModelAndView interfazPrestamo(){
        ModelAndView mav = new ModelAndView("prestamo-ui");
        mav.addObject("prestamo", ps.buscarTodos());
        mav.addObject("title", "Menu Gestión: Prestamos");
        return mav;
    }
    
    @GetMapping("/v2")
    public ModelAndView v2(){
        ModelAndView mav = new ModelAndView("test-p");
        return mav;
    }
    
    
    
    
    @GetMapping("/crear")
    public ModelAndView crearPrestamo(){       
        ModelAndView mav = new ModelAndView("prestamo-formulario");
        mav.addObject("libro",ls.buscarTodos());
        mav.addObject("clientes",cs.buscarTodos());
        mav.addObject("prestamo", new Prestamo());
        mav.addObject("title", "Crear Prestamo");
        mav.addObject("action", "guardar");
        return mav;      
    }
    
    
    @GetMapping("/editar/{id}")
    public ModelAndView editarPrestamo(@PathVariable String id){
        ModelAndView mav = new ModelAndView("prestamo-formulario");
        mav.addObject("libro",ls.buscarTodos());
        mav.addObject("clientes",cs.buscarPorDni(ps.buscarPrestamoporID(id).getCliente().getDocumento()));
        mav.addObject("prestamo",ps.buscarPrestamoporID(id) );
        mav.addObject("title", "Modificar Prestamo");
        mav.addObject("action", "modificar");
        return mav;
    }
    
    
    @PostMapping("/modificar")
    public RedirectView modificar(@RequestParam("id") String id,@RequestParam @DateTimeFormat (pattern="yyyy-MM-dd") Date devolucion, @RequestParam("libro") List<Libro> libro){
        try{ps.editarPrestamo(id, devolucion, libro);}
        catch(Exception e){
            return new RedirectView("/gestion/prestamo?edit=error");
        }
        return new RedirectView("/gestion/prestamo?edit=sucess");
    }
    
    @PostMapping("/guardar")
    public RedirectView guardar(@RequestParam @DateTimeFormat (pattern="yyyy-MM-dd") Date devolucion,@RequestParam("libro") List<Libro> libro, @RequestParam("cliente") Long dniCliente) throws ErrorService{      
        ps.cargarPrestamo(devolucion, libro, dniCliente);
        return new RedirectView("/gestion/prestamo");
        
    }
 
    @PostMapping("/eliminar/{id}")
    public RedirectView eliminar(@PathVariable String id){
        ps.eliminar(id);
        return new RedirectView("/gestion/prestamo?eliminar=success");
    }
    
    
}
