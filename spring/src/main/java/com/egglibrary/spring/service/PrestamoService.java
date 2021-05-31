
package com.egglibrary.spring.service;

import com.egglibrary.spring.entity.Cliente;
import com.egglibrary.spring.entity.Libro;
import com.egglibrary.spring.entity.Prestamo;
import com.egglibrary.spring.errores.ErrorService;
import com.egglibrary.spring.repository.PrestamoRepository;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PrestamoService {
    
    
    @Autowired
    private PrestamoRepository pr;
    @Autowired
    private ClienteService cs;
    
    
    //Cargar prestamo
    @Transactional
    public void cargarPrestamo(Date devolucion, List<Libro> libros, Long dniCliente) throws ErrorService {
        Prestamo p = new Prestamo();
        p.setFecha(new Date());
        p.setDevolucion(devolucion);
        
      //Verificacion cliente no nulo  
        if (cs.buscarPorDni(dniCliente) instanceof Cliente) {
           //Verificacion no tener mas de 3 prestamos  
           if(!cantMaxPrestamosPorCliente(dniCliente)){            
              p.setCliente(cs.buscarPorDni(dniCliente));
              //Verificacion de Stock de libros
               for (Libro libro : libros) {               
                   if(!(libro.getPrestados()==libro.getEjemplares())){
                       libro.setPrestados(libro.getPrestados()+1);
                       //se persiste solo a la DB
                   }else{
                       throw new ErrorService("No hay stock del libro: "+libro.getTitulo());
                   }
                   
               }
           }else{
               throw new ErrorService("El cliente posee 3 prestamos, debe realizar devolucion para solicitar otro.");
           }
          
        } else {
            throw new ErrorService("Cliente es null");
        }
        
        p.setLibro(libros);

        pr.save(p);

    }
    
    @Transactional(readOnly = true)
    public boolean cantMaxPrestamosPorCliente(Long dniCliente){
        List<Prestamo> prestamos = pr.cantidadDePrestamos(dniCliente);
        int activos=0;
        for (Prestamo prestamo : prestamos) {
            if(!prestamo.isDevuelto()){
                activos++;
            }
          if(activos==3){
            return true;
          }         
        }
        
        return false;
    }
    
    
    @Transactional
    public void editarPrestamo(String idPrestamo, Date devolucion, List<Libro> libros){
        pr.modificar(idPrestamo, devolucion, libros);
     
    }
    
    @Transactional
    public void devolver(String idPrestamo){
        Prestamo p = pr.findById(idPrestamo).get();
        Date fechaActual = new Date();
        //Devuelvo el libro a stock
        for (Libro libro : p.getLibro()) {
            libro.setPrestados(libro.getPrestados()-1);
        }
        //Si fecha actual > fecha devolucion fijada = multa
        if(p.getDevolucion().after(fechaActual)){
            p.setMulta(100.25);
        }
        p.setDevolucion(fechaActual);
        //Flag devuelto
        p.setDevuelto(true);
        pr.devolver(idPrestamo);          
    }

    @Transactional(readOnly = true)
    public List<Prestamo> buscarTodos() {
        return pr.findAll();
    }
    
    @Transactional(readOnly = true)
    public Prestamo buscarPrestamoporID(String id){
        return pr.findById(id).orElse(null);
    }
    
    @Transactional
    public void eliminar(String id){
        pr.deleteById(id);
    }
            
    
            
}
