package com.egglibrary.spring.controller;


import com.egglibrary.spring.entity.Editorial;
import com.egglibrary.spring.entity.Rol;
import com.egglibrary.spring.repository.UsuarioRepository;
import com.egglibrary.spring.service.RolService;
import com.egglibrary.spring.service.UsuarioService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;


@Controller
@RequestMapping("/adm/usuarios")
@PreAuthorize("hasRole('ADMIN')")
public class usuarioController {

    @Autowired
    private UsuarioService us;
    @Autowired
    private RolService rs;


    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView mostrarUsuarios(){
        ModelAndView mav = new ModelAndView("usuario-lista");
        mav.addObject("usuarios",us.listaUsuarios());
        mav.addObject("title","Menu Gestion: Usuarios");

        return mav;
    }

    @GetMapping("/roles")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView mostrarRoles(){
        ModelAndView mav = new ModelAndView("roles-lista");
        mav.addObject("roles", rs.listadeRoles());
        mav.addObject("title","Menu Gestion: Roles");
        return mav;
    }


    @GetMapping("/roles/crear")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView crearRol() {
        ModelAndView mav = new ModelAndView("rol-formulario");
        mav.addObject("rol", new Rol());
        mav.addObject("title", "Crear Rol");
        mav.addObject("action", "guardar");
        return mav;
    }


    @GetMapping("/roles/editar/{id}")
    public ModelAndView editarRol(@PathVariable String id){
        ModelAndView mav = new ModelAndView("rol-formulario");
        mav.addObject("rol",rs.buscarporID(id));
        mav.addObject("title", "Editar Rol");
        mav.addObject("action", "modificar");
        return mav;
    }


    @PostMapping("/roles/guardar")
    @PreAuthorize("hasRole('ADMIN')")
    public RedirectView guardar(@RequestParam String nombre){
        rs.crear(nombre);
        return new RedirectView("/adm/usuarios");
    }






}
