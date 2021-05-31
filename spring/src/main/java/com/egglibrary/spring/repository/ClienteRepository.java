package com.egglibrary.spring.repository;

import com.egglibrary.spring.entity.Cliente;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository                                             //Objeto , Clave primaria en DB
public interface ClienteRepository extends JpaRepository <Cliente, Long>{
    
    @Modifying
    @Query("UPDATE Cliente c SET c.nombre = :nombre, c.apellido = :apellido, c.domicilio = :domicilio, c.telefono = :telefono WHERE c.documento = :documento")
    void modificar(@Param("documento") Long documento, @Param("nombre") String nombre, @Param("apellido") String apellido, @Param("domicilio") String domicilio, @Param("telefono") String telefono);
    
    
    
    
}
