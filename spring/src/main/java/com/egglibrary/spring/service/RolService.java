package com.egglibrary.spring.service;

import com.egglibrary.spring.entity.Autor;
import com.egglibrary.spring.entity.Rol;
import com.egglibrary.spring.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RolService {

    @Autowired
    RolRepository rp;

    @Transactional
    public void crear(String nombre){
        Rol rol = new Rol();
        rol.setNombre(nombre);
        rp.save(rol);
    }

    @Transactional(readOnly = true)
    public Rol buscarporID(String id){
        Optional<Rol> rolOptional = rp.findById(id);
        return rolOptional.orElse(null);
    }

    @Transactional
    public void eliminar(String id){ rp.deleteById(id);}

    @Transactional(readOnly = true)
    public List<Rol> listadeRoles(){
        return rp.findAll();
    }

}
