package kurakh.beautysalon.repository;

import kurakh.beautysalon.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByName(String name);

    Optional<User> findByUsername(String username);

    boolean existsByName(String name);

    Optional<User> findById(String id);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Page<User> findAllByNameLike(Pageable pageable, String name);

}
