
package com.egglibrary.spring.repository;

import com.egglibrary.spring.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    
    Usuario findByusername(String username);
    
}
