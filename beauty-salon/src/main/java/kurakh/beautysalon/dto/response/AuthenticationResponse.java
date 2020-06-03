package kurakh.beautysalon.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class AuthenticationResponse {
//    private String username;
//    private String token;
//
    public AuthenticationResponse(String username, String token) {
        this.username = username;
        this.token = token;
    }

    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String email;
    private List<String> roles;
}
