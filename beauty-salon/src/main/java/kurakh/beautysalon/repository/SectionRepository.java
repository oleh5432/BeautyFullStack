package kurakh.beautysalon.repository;

import kurakh.beautysalon.entity.Section;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {

    Optional<Section> findByName(String name);

    Page<Section> findAllByNameLike(Pageable pageable, String name);
}
