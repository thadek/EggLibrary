
package com.egglibrary.spring.repository;

import com.egglibrary.spring.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface RolRepository extends JpaRepository <Rol,String> {

}
