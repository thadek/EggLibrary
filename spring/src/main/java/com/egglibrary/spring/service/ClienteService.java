package com.egglibrary.spring.service;

import com.egglibrary.spring.entity.Cliente;
import com.egglibrary.spring.repository.ClienteRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
public class ClienteService {
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    @Transactional
    public void crear(Long dni, String nombre, String apellido, String domicilio, String telefono){
        Cliente cliente = new Cliente();
        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setDocumento(dni);
        cliente.setDomicilio(domicilio);
        cliente.setTelefono(telefono);
       //Guardo los datos en la DB 
       clienteRepository.save(cliente);
    }
    
    @Transactional
    public void modificar(Long dni, String nombre, String apellido, String domicilio, String telefono){
        clienteRepository.modificar(dni, nombre, apellido, domicilio, telefono);  
    }
    
    @Transactional(readOnly = true)
    public List<Cliente> buscarTodos() {
        return clienteRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Cliente buscarPorDni(Long dni) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(dni);
        return clienteOptional.orElse(null);
    }

    @Transactional
    public void eliminar(Long documento) {
        clienteRepository.deleteById(documento);
    }
    
    
    
}
