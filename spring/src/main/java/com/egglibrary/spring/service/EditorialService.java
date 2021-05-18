
package com.egglibrary.spring.service;

import com.egglibrary.spring.entity.Editorial;
import com.egglibrary.spring.repository.EditorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    
    
}
