package com.egglibrary.spring.repository;


import com.egglibrary.spring.entity.Editorial;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



@Repository   
public interface EditorialRepository extends JpaRepository<Editorial,String>{
    
   @Query("SELECT e FROM Editorial e WHERE e.nombre = :nombre")
    public List<Editorial>buscarEditorialporNombre(@Param("nombre") String nombre);
    
    @Modifying
    @Query("UPDATE Editorial e SET e.nombre = :nombre WHERE e.id = :id")
    void modificar(@Param("nombre") String nombre, @Param("id") String id);
   
    
}
