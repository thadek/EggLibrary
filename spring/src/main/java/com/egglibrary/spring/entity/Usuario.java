
package com.egglibrary.spring.entity;

import javax.persistence.*;

import com.egglibrary.spring.entity.Rol;
import lombok.Data;

@Data
@Entity
public class Usuario {
    
    @Id
    private int id;
    @Column(unique = true)
    private String username;
    @Column(unique=true)
    private String email;
    private String password;
    @OneToOne
    private Cliente cliente;
    @ManyToOne
    private Rol rol;


    
}
