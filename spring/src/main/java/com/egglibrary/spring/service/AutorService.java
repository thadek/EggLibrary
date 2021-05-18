
package com.egglibrary.spring.service;

import com.egglibrary.spring.entity.Autor;
import com.egglibrary.spring.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AutorService {
    
    @Autowired
    private AutorRepository autorRepository;
    
    @Transactional 
    public void crear(String nombre){
        Autor autor = new Autor();
        autor.setNombre(nombre);
        autorRepository.save(autor);
    }
    @Transactional
    public void modificar(String nombre, String id){
        autorRepository.modificar(id, nombre);
    }
    
}
