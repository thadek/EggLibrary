package com.egglibrary.spring.entity;

import com.egglibrary.spring.enums.Estado;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Cliente {
    
    @Id
    private Long documento;
    private String nombre;
    private String apellido;
    private String domicilio;
    private String telefono;
    private Double multas;
    private Estado estadoCliente;

    //Relaciones - Prestamos
    @OneToMany(mappedBy = "cliente",fetch = FetchType.EAGER)
    private List<Prestamo> prestamos;
    
    
    public Cliente() {
    }

    public Long getDocumento() {
        return documento;
    }

    public void setDocumento(Long documento) {
        this.documento = documento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Double getMultas() {
        return multas;
    }

    public void setMultas(Double multas) {
        this.multas = multas;
    }

    public Estado getEstadoCliente() {
        return estadoCliente;
    }

    public void setEstadoCliente(Estado estadoCliente) {
        this.estadoCliente = estadoCliente;
    }
    
    
    
    
}
