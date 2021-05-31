
package com.egglibrary.spring.controller;

import com.egglibrary.spring.entity.Libro;
import com.egglibrary.spring.service.AutorService;
import com.egglibrary.spring.service.EditorialService;
import com.egglibrary.spring.service.LibroService;
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
@RequestMapping("/gestion/libro")
public class libroController {
    
    @Autowired 
    private LibroService ls;
    @Autowired 
    private AutorService as;
    @Autowired 
    private EditorialService es;
    
    
    
    @GetMapping
    public ModelAndView listar(){
        ModelAndView mav = new ModelAndView("libro-lista");
        mav.addObject("libro",ls.buscarTodos());
        mav.addObject("title", "Menu Gestión: Libro");
        
        return mav;
    }
    
    @GetMapping("/editar/{isbn}")
    public ModelAndView editarLibro(@PathVariable Long isbn){
        ModelAndView mav = new ModelAndView("libro-formulario");
        mav.addObject("autor",as.buscarTodos());
        mav.addObject("editorial",es.buscarTodos());
        mav.addObject("libro",ls.buscarporISBN(isbn));
        mav.addObject("title","Editar Libro");
        mav.addObject("action","modificar");
        return mav;
    }

    @GetMapping("/crear")
    public ModelAndView crearLibro() {
        ModelAndView mav = new ModelAndView("libro-formulario");
        mav.addObject("autor",as.buscarTodos());
        mav.addObject("editorial",es.buscarTodos());
        mav.addObject("libro", new Libro());
        mav.addObject("title", "Crear Libro");
        mav.addObject("action", "guardar");
        return mav;
    }
    
@PostMapping("/eliminar/{isbn}")
    public RedirectView eliminar(@PathVariable Long isbn){
         
        try{ls.eliminar(isbn);}catch(Exception e){
            return new RedirectView("/gestion/libro?error=eliminar");
        }
        
        return new RedirectView("/gestion/libro");
    }

    @PostMapping("/modificar")
    public RedirectView modificar(@RequestParam Long isbn, @RequestParam String titulo, @RequestParam Integer anio, @RequestParam Integer ejemplares){
        ls.modificar(isbn, titulo, anio , ejemplares);
        return new RedirectView("/gestion/libro");
    }    
    
    @PostMapping("/guardar")
    public RedirectView guardar(@RequestParam Long isbn, @RequestParam String titulo,@RequestParam("autor") String idautor, @RequestParam("editorial") String idEditorial, @RequestParam Integer anio, @RequestParam Integer ejemplares){
        try{
            ls.crear(isbn, titulo, ejemplares, anio, idautor, idEditorial);
        }catch(Exception e){
            return new RedirectView("/gestion/libro?error=crear");
        }
        return new RedirectView("/gestion/libro");
    }
    
    
}
