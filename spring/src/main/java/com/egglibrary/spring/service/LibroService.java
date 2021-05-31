package com.egglibrary.spring.service;

import com.egglibrary.spring.entity.Autor;
import com.egglibrary.spring.entity.Libro;
import com.egglibrary.spring.errores.ErrorService;
import com.egglibrary.spring.repository.AutorRepository;
import com.egglibrary.spring.repository.EditorialRepository;
import com.egglibrary.spring.repository.LibroRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LibroService {
    
    @Autowired
    private LibroRepository lr;
    @Autowired
    private AutorRepository ar;
    @Autowired
    private EditorialRepository er;
    
    @Transactional
    public void crear(Long isbn, String titulo, Integer ejemplares, Integer anio, String idautor, String idEditorial) throws ErrorService{
        try{
            
            Libro libro = new Libro();
        libro.setAnio(anio);
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setEjemplares(ejemplares);
        libro.setPrestados(0);          
        libro.setAutor(ar.findById(idautor).orElse(null));          
        libro.setEditorial(er.findById(idEditorial).orElse(null));
        lr.save(libro);
            
        }catch(Exception e){
            throw new ErrorService(e.getMessage());
            
        }
        
        
      
    }
    
    
    
    @Transactional
    public void modificar(Long isbn, String titulo,Integer anio, Integer ejemplares){
       lr.modificar(isbn, titulo, anio, ejemplares);
    }
    
    
    @Transactional(readOnly = true)
    public List<Libro> buscarTodos(){
        return lr.findAll();
    }
    
    
    @Transactional(readOnly = true)
    public Libro buscarporISBN(Long isbn){
        Optional<Libro> librooptional = lr.findById(isbn);
        return librooptional.orElse(null);
    }
    
    @Transactional
    public void eliminar(Long isbn){
        lr.deleteById(isbn);
    }
}


