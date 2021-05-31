
package com.egglibrary.spring.controller;
import com.egglibrary.spring.restservice.userValidator;
import com.egglibrary.spring.service.PrestamoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class restapiController {
    
    @Autowired
    PrestamoService ps = new PrestamoService();
    private static final String template = "Probando, %s!";
    
    @GetMapping("/prueba")
    public String greeting(@RequestParam(value ="name", defaultValue="Confrio") String name){
        return new String(String.format(template,name));
    }
    
    
    @GetMapping("/validator/prestamos/prestados-by-dni/max/{dni}")
    public userValidator validarPrestadosMaximos(@PathVariable("dni") Long dni){
        return new userValidator(ps.cantMaxPrestamosPorCliente(dni));
    }
    
}
