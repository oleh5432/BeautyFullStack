package kurakh.beautysalon.service;

import kurakh.beautysalon.entity.ERole;
import kurakh.beautysalon.entity.Role;
import kurakh.beautysalon.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    RoleRepository roleRepository;

    public Role getUserRole (ERole name) {
        return roleRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
    }
}
