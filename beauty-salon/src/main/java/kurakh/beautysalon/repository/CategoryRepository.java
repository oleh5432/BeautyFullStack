package kurakh.beautysalon.repository;

import kurakh.beautysalon.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>, JpaSpecificationExecutor<Category> {

    Optional<Category> findByName(String name);

    Page<Category> findAllByNameLike(Pageable pageable, String name);

    Page<Category> findAllBySection_Id(Pageable pageable, List<Long> sectionsId);

//    Page<Category> findAllBySectionId(Pageable pageable, List<Long> sectionsId);


}
