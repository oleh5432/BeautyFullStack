package kurakh.beautysalon.repository;

import kurakh.beautysalon.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
//    List<Product> findAllByNameLikeAndPriceLessThan(String name, Integer maxPrice);

    Page<Product> findAllByNameLike(Pageable pageable, String name);

    List<Product> findProductsById(List<Long> productsId);
}
