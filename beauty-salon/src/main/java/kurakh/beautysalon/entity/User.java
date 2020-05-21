package kurakh.beautysalon.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "_user")
public class User{
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(nullable = false)
    private String name;

    private String phoneNumber;

    @Column(nullable = false)
    private UserRole userRole;

    private String userpic;

    private String email;

    private String gender;

    private String locale;

    @Column(nullable = false, unique = true)
    private String username; //login

    @Column(nullable = false)
    private String password;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastVisit;

    @OneToMany(mappedBy = "user")
    private List<Order> orders = new ArrayList<>();
}
