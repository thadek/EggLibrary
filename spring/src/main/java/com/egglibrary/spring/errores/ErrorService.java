
package com.egglibrary.spring.errores;


public class ErrorService extends Exception{
       
    public ErrorService(String mensaje){
        super(mensaje);
    }
    
}
