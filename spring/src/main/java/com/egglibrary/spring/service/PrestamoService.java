
package com.egglibrary.spring.service;

import com.egglibrary.spring.entity.Cliente;
import com.egglibrary.spring.entity.Libro;
import com.egglibrary.spring.entity.Prestamo;
import com.egglibrary.spring.errores.ErrorService;
import com.egglibrary.spring.repository.PrestamoRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PrestamoService {
    
    
    @Autowired
    private PrestamoRepository pr;
    @Autowired
    private ClienteService cs;
    
    
    public boolean stocklleno(Libro l){
        
        if(l.getPrestados()==l.getEjemplares()){
            return true;
        }else{
            return false;
        }
    }
    
    
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
                   if(!stocklleno(libro)){
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
    public void editarPrestamo(String idPrestamo, Date devolucion, List<Libro> libros) throws ErrorService{
        
        Optional<Prestamo> prestamoBuscado = pr.findById(idPrestamo);
        
        if(prestamoBuscado.isPresent()){
            Prestamo p = prestamoBuscado.get();
            p.setDevolucion(devolucion);
            for (Libro libro : p.getLibro()) {
                libro.setPrestados(libro.getPrestados()-1);
            }
            for (Libro libro : libros) {
                if(!stocklleno(libro)){
                    libro.setPrestados(libro.getPrestados()+1);
                }else{
                    throw new ErrorService("NO HAY STOCK DEL LIBRO "+ libro.getTitulo());
                }           
            }
            
            p.setLibro(libros);        
            pr.save(p);
        }else{
            throw new ErrorService("No existe ese id prestamo.");
        }
        
        
        //Deprecado
        //pr.modificar(idPrestamo, devolucion, libros);
     
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
            p.setMulta(calcularmulta(p.getDevolucion(),fechaActual));
        }
        p.setDevolucion(fechaActual);
        //Flag devuelto
        p.setDevuelto(true);
        pr.devolver(idPrestamo);          
    }
    
    public Double calcularmulta(Date devEstimada, Date devReal){
        double multa=0;
        if(devEstimada.after(devReal)){
            
            long milisecondsByDay = 86400000;
            
            long diferencia = ((devReal.getTime()-devEstimada.getTime())/milisecondsByDay);
            
            if(diferencia<5){
                multa=100;
            }else if(diferencia>5 && diferencia<15){
                multa=500;
            }else{
                multa=diferencia*125;
            }      
        }
        return multa;
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
        devolver(id);
        pr.deleteById(id);
    }
            
    
            
}
