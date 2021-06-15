
package com.egglibrary.spring.service;

import com.egglibrary.spring.entity.Usuario;
import com.egglibrary.spring.repository.UsuarioRepository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.egglibrary.spring.errores.ErrorService;

import javax.servlet.http.HttpSession;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService implements UserDetailsService{
    
    @Autowired
    private UsuarioRepository repo;
    
    
    private String validar(String username, String email, String password, String password2) throws ErrorService{
        if(username==null){
          throw new ErrorService("Username no puede ser nulo");  
        }
        if(email==null){
            throw new ErrorService("Email no puede ser nulo");
        }
        if(!(password.equalsIgnoreCase(password2))){
            throw new ErrorService("Las contraseñas no coinciden");
        }
        
        return password;
        
    }
    
    @Transactional
    public void registrar(String username, String password1, String password2, String email) throws ErrorService {
        String passwordvalid=validar(username,email, password1, password2);
        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setEmail(email);
        //usuario.setRol();
        
        //Clave encriptada
        String encripted = new BCryptPasswordEncoder().encode(passwordvalid);
        usuario.setPassword(encripted);
        repo.save(usuario);
    }
    

    public List<Usuario> listaUsuarios(){
        return repo.findAll();
    }

    
    
    
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        Usuario us = repo.findByusername(username);
        List<GrantedAuthority> roles = new ArrayList<>();
     if(us!=null){
         /*GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_USER_REGISTRADO");
         roles.add(p1);
       GrantedAuthority p2 = new SimpleGrantedAuthority("ROLE_USER_EMPLEADO");
        roles.add(p2);*/
        GrantedAuthority p3 = new SimpleGrantedAuthority("ROLE_USER_ADMIN");
        roles.add(p3);

         //Fetcheo los datos de la sesion de usuario
         ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
         HttpSession session = attr.getRequest().getSession(true);
         
         session.setAttribute("usuariosession",us);
      
         UserDetails usDet = new User (us.getUsername(),us.getPassword(),roles);
         return usDet;
     }else{
         return null;
     }


    }
    
}
