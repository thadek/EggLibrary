
package com.egglibrary.spring.repository;

import com.egglibrary.spring.entity.Autor;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorRepository extends JpaRepository <Autor,String> {
    
     @Query("SELECT a FROM Autor a WHERE a.nombre = :nombre")
    public List<Autor>buscarAutorporNombre(@Param("nombre") String nombre);
    
    @Modifying
    @Query("UPDATE Autor a SET a.nombre = :nombre WHERE a.id = :id")
    void modificar(@Param("id") String id,@Param("nombre") String nombre );
    
    
    
    
}
