
package com.egglibrary.spring.repository;

import com.egglibrary.spring.entity.Libro;
import com.egglibrary.spring.entity.Prestamo;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PrestamoRepository  extends JpaRepository<Prestamo, String>{
    
    @Modifying
    @Query("UPDATE Prestamo p SET p.devolucion = :devolucion , p.libro = :libros WHERE p.id = :id")
    void modificar(@Param("id") String id, @Param("devolucion") Date devolucion, @Param("libros")List<Libro> libros);
    
    
    @Query("SELECT p from Prestamo p WHERE p.cliente.documento = :documento")
    List<Prestamo> cantidadDePrestamos(@Param("documento") Long documento);
    
    @Modifying
    @Query("UPDATE Prestamo p SET p.devuelto=true WHERE p.id = :id")
    void devolver(@Param("id") String id);
    
}
