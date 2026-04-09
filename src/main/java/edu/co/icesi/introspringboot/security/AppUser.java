package edu.co.icesi.introspringboot.security;

import edu.co.icesi.introspringboot.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AppUser implements UserDetails {

    private User user;

    public AppUser(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for(int i=0 ; i<user.getUserRoles().size(); i++){
            String authority = user.getUserRoles().get(i).getRole().getName();
            SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority);
            authorities.add(grantedAuthority);

        }
        return authorities;
        //Aqui cargaré un arreglo de strings que simbolizan sus roles y permisos
        //ROLE_STUDENT, READ_EXCERSICES, CREATE_ROUTINE
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }
}
