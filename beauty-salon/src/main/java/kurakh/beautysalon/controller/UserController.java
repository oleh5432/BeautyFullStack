package kurakh.beautysalon.controller;

import kurakh.beautysalon.dto.request.UserRequest;
import kurakh.beautysalon.dto.request.UserRequestLogin;
import kurakh.beautysalon.dto.response.AuthenticationResponse;
import kurakh.beautysalon.dto.response.DataResponse;
import kurakh.beautysalon.dto.response.UserResponse;
import kurakh.beautysalon.exception.WrongInputDataException;
import kurakh.beautysalon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

//    @PostMapping
//    public UserResponse save(@RequestBody UserRequest userRequest) throws WrongInputDataException {
//        return userService.save(userRequest);
//    }

    @GetMapping
    public List<UserResponse> findAll() {
        return userService.findAll();
    }

    @GetMapping("/id")
    public UserResponse findOneUserById(@RequestParam String id) throws WrongInputDataException {
        return userService.findOneUserById(id);
    }

    @GetMapping("/page")
    public DataResponse<UserResponse> findAll(@RequestParam Integer page,
                                              @RequestParam Integer size,
                                              @RequestParam(required = false) Sort.Direction direction,
                                              @RequestParam(required = false) String fieldName,
                                              @RequestParam(required = false) String name
    ) {
        return userService.findAll(page, size, fieldName, direction, name);
    }

    @PutMapping
    public UserResponse update(@RequestParam String id,
                                   @RequestBody UserRequest userRequest) throws WrongInputDataException {
        return userService.update(id, userRequest);
    }

    @DeleteMapping
    public void delete(@RequestParam String id) throws WrongInputDataException {
        userService.delete(id);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@Valid @RequestBody UserRequestLogin request) {
        return userService.login(request);
    }

    @PostMapping("/register")
    public AuthenticationResponse register(@Valid @RequestBody UserRequest request) {
        return userService.register(request);
    }

    @GetMapping("/checkToken")
    public void checkToken() {
    }


    @GetMapping("/secured")
    public void test() {
    }

//    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/secured1")
    public void test1() {
    }

//    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/secured2")
    public void test2() {
    }
}

