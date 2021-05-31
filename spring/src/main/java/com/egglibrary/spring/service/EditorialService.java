
package com.egglibrary.spring.service;

import com.egglibrary.spring.entity.Editorial;
import com.egglibrary.spring.entity.Libro;
import com.egglibrary.spring.repository.EditorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EditorialService {
    
    @Autowired
    private EditorialRepository editorialrepository;
    
    @Transactional
    public void crear(String nombre){
        Editorial editorial = new Editorial();
        editorial.setNombre(nombre);
        editorialrepository.save(editorial);
        
    }

    @Transactional(readOnly = true)
    public Editorial buscarporID(String id){
        Optional<Editorial> editorialOptional = editorialrepository.findById(id);
        return editorialOptional.orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Editorial> buscarTodos(){
        return editorialrepository.findAll();
    }

    @Transactional
    public void modificar(String nombre, String id){
        editorialrepository.modificar(nombre, id);
    }
    @Transactional
    public void eliminar(String id){ editorialrepository.deleteById(id);}

    
}
