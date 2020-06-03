package kurakh.beautysalon.config;

import kurakh.beautysalon.entity.ERole;
import kurakh.beautysalon.entity.Role;
import kurakh.beautysalon.entity.User;
import kurakh.beautysalon.repository.UserRepository;
import kurakh.beautysalon.security.AuthEntryPointJwt;
import kurakh.beautysalon.security.JwtConfigure;
import kurakh.beautysalon.security.JwtTokenTool;
import kurakh.beautysalon.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.time.LocalDateTime;
import java.util.*;

@Configuration
@EnableWebSecurity
@EnableOAuth2Sso
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtTokenTool jwtTokenTool;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Autowired
    RoleService roleService;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .authorizeRequests()
                .antMatchers("/", "/user/login", "/login**", "/js/**", "/error**").permitAll()
                .antMatchers(HttpMethod.POST, "/user/login", "/user/register").permitAll()
//                .antMatchers(HttpMethod.GET, "/user/checkToken").hasAnyRole("ADMIN")
//                .antMatchers(HttpMethod.GET, "/admin").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.GET).permitAll()
                .antMatchers("/api/test/**").permitAll()
                .antMatchers("/img/**").permitAll()
                .anyRequest().hasAnyRole("ADMIN")
                .anyRequest().authenticated()
                .and().logout().logoutSuccessUrl("/").permitAll()
                .and()
                .apply(new JwtConfigure(jwtTokenTool));;
    }

    private Role getUserRole(ERole name) {
      return roleService.getUserRole(name);
    };

    @Bean
    public PrincipalExtractor principalExtractor(UserRepository userDetailsRepo) {
        return map -> {
            String id = (String) map.get("sub");
            String email = (String) map.get("email");

//            User user = userDetailsRepo.findById(id).orElseGet(() -> {
            User user = userDetailsRepo.findByUsername(email).orElseGet(() -> {
                User newUser = new User();

                newUser.setId(id);
                newUser.setName((String) map.get("name"));
                newUser.setEmail(email);
                newUser.setGender((String) map.get("gender"));
                newUser.setLocale((String) map.get("locale"));
                newUser.setUserpic((String) map.get("picture"));
//                newUser.setUserRole(UserRole.USER);
                Set<Role> roles = new HashSet<>();
                ERole roleNameUser = ERole.ROLE_USER;
                roles.add(getUserRole(roleNameUser));
                newUser.setRoles(roles);
                newUser.setUsername(email);
                newUser.setPassword(encoder.encode("password")); // надсилати на email повідомлення про заміню пароля

                return newUser;
            });

            user.setLastVisit(LocalDateTime.now());

            return userDetailsRepo.save(user);
        };
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS", "DELETE", "PUT", "PATCH"));
        configuration.setAllowedHeaders(Arrays.asList("X-Requested-With", "Origin", "Content-Type", "Accept", "Authorization"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");

    }
}

