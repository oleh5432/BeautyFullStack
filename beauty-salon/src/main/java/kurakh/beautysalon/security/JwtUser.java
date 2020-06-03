package kurakh.beautysalon.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kurakh.beautysalon.entity.User;
import kurakh.beautysalon.entity.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class JwtUser implements UserDetails {
    private static final long serialVersionUID = 1L;

    @JsonIgnore
    private String password;
    private String username; //login
    private UserRole userRole;
    private String phoneNumber;
    private String email;
    private String name;

    private Collection<? extends GrantedAuthority> authorities;

    public JwtUser(String password, String login, String phoneNumber, String email, String name,
                   Collection<? extends GrantedAuthority> authorities) {
        this.password = password;
        this.username = login;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.name = name;
        this.authorities = authorities;
    }

    public static JwtUser build(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

        return new JwtUser(
                user.getPassword(),
                user.getUsername(),
                user.getPhoneNumber(),
                user.getEmail(),
                user.getName(),
                authorities);
    }

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return Collections.singletonList(new SimpleGrantedAuthority(userRole.name()));
//    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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
