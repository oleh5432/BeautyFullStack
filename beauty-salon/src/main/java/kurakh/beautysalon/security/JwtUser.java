package kurakh.beautysalon.security;

import kurakh.beautysalon.entity.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class JwtUser implements UserDetails {

    private String password;
    private String username; //login
    private UserRole userRole;
    private String phoneNumber;
    private String email;
    private String name;

//    public JwtUser(String login, UserRole userRole, String password) {
//        this.username = login;
//        this.userRole = userRole;
//        this.password = password;
//    }


    public JwtUser(String password, String login, UserRole userRole, String phoneNumber, String email, String name) {
        this.password = password;
        this.username = login;
        this.userRole = userRole;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.name = name;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(userRole.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
