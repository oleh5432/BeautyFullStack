package kurakh.beautysalon.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserRequest {

    @NotBlank
    private String name;

    private String phoneNumber;

    private String email;

    @NotBlank
    private String username; //login

    @Size(min = 4, max = 30)
    private String password;
}
