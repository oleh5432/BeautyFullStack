package kurakh.beautysalon.service;

import kurakh.beautysalon.dto.request.UserRequest;
import kurakh.beautysalon.dto.request.UserRequestLogin;
import kurakh.beautysalon.dto.response.AuthenticationResponse;
import kurakh.beautysalon.dto.response.DataResponse;
import kurakh.beautysalon.dto.response.MessageResponse;
import kurakh.beautysalon.dto.response.UserResponse;
import kurakh.beautysalon.entity.ERole;
import kurakh.beautysalon.entity.Role;
import kurakh.beautysalon.entity.User;
import kurakh.beautysalon.exception.WrongInputDataException;
import kurakh.beautysalon.repository.RoleRepository;
import kurakh.beautysalon.repository.UserRepository;
import kurakh.beautysalon.security.JwtTokenTool;
import kurakh.beautysalon.security.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService  implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    RoleService roleService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenTool jwtTokenTool;

    @Autowired
    private BCryptPasswordEncoder encoder;

//    public UserResponse save(UserRequest userRequest) throws WrongInputDataException {
//        return new UserResponse(userRepository.save(userRequestToUser(userRequest, null)));
//    }

    public List<UserResponse> findAll() {
        return userRepository.findAll().stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
    }

    public DataResponse<UserResponse> findAll(Integer page, Integer size, String fieldName, Sort.Direction direction, String name) {
        if (direction == null) {
            direction = Sort.Direction.ASC;
        }
        if (fieldName == null) {
            fieldName = "id";
        }

        Page<User> categoriesPage = name == null ?
                userRepository.findAll(PageRequest.of(page, size, direction, fieldName)) :
                userRepository.findAllByNameLike(PageRequest.of(page, size, direction, fieldName), '%' + name + '%');

        return new DataResponse<>(
                categoriesPage.get().map(UserResponse::new).collect(Collectors.toList()),
                categoriesPage.getTotalPages(),
                categoriesPage.getTotalElements()
        );
    }

    public UserResponse update(String id, UserRequest userRequest) throws WrongInputDataException {
        return new UserResponse(userRepository
                .save(userRequestToUser(userRequest, findOneById(id))));
    }

    private User userRequestToUser(UserRequest userRequest, User user) throws WrongInputDataException {
        if (user == null) {
            user = new User();
        }
        user.setName(userRequest.getName());
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setEmail(userRequest.getEmail());
        user.setUsername(userRequest.getUsername()); //login
        user.setPassword(encoder.encode(userRequest.getPassword()));
//        user.setUserRole(UserRole.USER);
        return user;
    }

    public void delete(String id) {
        userRepository.deleteById(id);
    }

    public User findOneById(String id) throws WrongInputDataException {
        return userRepository.findById(id)
                .orElseThrow(() -> new WrongInputDataException("User with id '" + id + "' not exists"));

    }


    public UserResponse findOneUserById(String id) throws WrongInputDataException {
        return new UserResponse(userRepository.findById(id)
                .orElseThrow(() -> new WrongInputDataException("User with id '" + id + "' not exists")));

    }

    public AuthenticationResponse register(UserRequest request) {
        String username = request.getUsername();
        String email = request.getEmail();
        if (userRepository.existsByUsername(username)) {
            throw new BadCredentialsException("User with login " + username + " already exists");
//            return ResponseEntity
//                    .badRequest()
//                    .body(new MessageResponse("Error: Username is already taken!"));
        }
        if (userRepository.existsByEmail(email)) {
            throw new BadCredentialsException("User with email " + email + " already exists");
//            return ResponseEntity
//                    .badRequest()
//                    .body(new MessageResponse("Error: Email is already use!"));
        }

        // Create new user's account
        User user = new User();

        user.setName(request.getName());
        user.setUsername(username);
        user.setEmail(email);
        user.setPhoneNumber(request.getPhoneNumber());;
        user.setPassword(encoder.encode(request.getPassword()));

        Set<String> strRoles = request.getRole();
        Set<Role> roles = new HashSet<>();
        ERole roleNameUser = ERole.ROLE_USER;
        ERole roleNameModerator = ERole.ROLE_MODERATOR;
        ERole roleNameAdmin = ERole.ROLE_ADMIN;

        if (strRoles == null) {
            roles.add(roleService.getUserRole(roleNameUser));
        } else {

            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        roles.add(roleService.getUserRole(roleNameAdmin));
                        break;
                    case "mod":
                        roles.add(roleService.getUserRole(roleNameModerator));
                        break;
                    default:
                        roles.add(roleService.getUserRole(roleNameUser));
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        UserRequestLogin userRequestLogin = new UserRequestLogin();

        userRequestLogin.setUsername(username);
        userRequestLogin.setPassword((request.getPassword()));

        return login(userRequestLogin);
    }

    public AuthenticationResponse login(UserRequestLogin request) {
        String username = request.getUsername();
        User user = findByUsername(username);
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, request.getPassword()));
//        String token = jwtTokenTool.createToken(username , user.getUserRole());
        String token = jwtTokenTool.createToken(username);
        user.setLastVisit(LocalDateTime.now());
        userRepository.save(user);
        return new AuthenticationResponse(username, token);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
        return JwtUser.build(user);
    }

    private User findByUsername(String username)  {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with login " + username + " not exists"));
    }
}
