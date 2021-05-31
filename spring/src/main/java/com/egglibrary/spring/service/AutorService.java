
package com.egglibrary.spring.service;

import com.egglibrary.spring.entity.Autor;
import com.egglibrary.spring.entity.Editorial;
import com.egglibrary.spring.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

    @Transactional(readOnly = true)
    public Autor buscarporID(String id){
        Optional<Autor> autorOptional = autorRepository.findById(id);
        return autorOptional.orElse(null);
    }

    @Transactional
    public void eliminar(String id){ autorRepository.deleteById(id);}

    @Transactional(readOnly = true)
    public List<Autor> buscarTodos(){
        return autorRepository.findAll();
    }

}
