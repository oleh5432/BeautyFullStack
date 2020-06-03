package kurakh.beautysalon.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
public class UserRequest {

    @NotBlank
    private String name;

//    @NumberFormat()
    private String phoneNumber;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(min = 3)
    private String username; //login

    private Set<String> role;

    @Size(min = 4, max = 40)
    private String password;
}
