package kurakh.beautysalon.dto.response;

import kurakh.beautysalon.entity.User;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserResponse {
    private String id;

    private String name;

    private String phoneNumber;

    private String email;

    public UserResponse (User user) {
        id = user.getId();
        name = user.getName();
        phoneNumber = user.getPhoneNumber();
        email = user.getEmail();
    }
}
