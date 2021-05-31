package com.egglibrary.spring.repository;

import com.egglibrary.spring.entity.Libro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepository extends JpaRepository <Libro,Long> {
    
    @Query("SELECT l FROM Libro l WHERE l.isbn = :isbn")
    public List<Libro>buscarLibroporISBN(@Param("isbn") Long isbn);
    
    @Modifying
    @Query("UPDATE Libro l SET l.titulo = :titulo, l.anio = :anio, l.ejemplares = :ejemplares WHERE l.isbn = :isbn")
    void modificar(@Param("isbn") Long isbn,@Param("titulo") String titulo, @Param("anio") Integer anio,@Param("ejemplares") Integer ejemplares);
    
}
