package kurakh.beautysalon.specification;

import kurakh.beautysalon.dto.request.ProductFilterRequest;
import kurakh.beautysalon.entity.Category;
import kurakh.beautysalon.entity.Product;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class ProductSpecification implements Specification<Product> {

    private ProductFilterRequest filter;

    private List<Predicate> predicates = new ArrayList<>();

    public ProductSpecification(ProductFilterRequest filter) {
        this.filter = filter;
    }

    @Override
    public Predicate toPredicate(Root<Product> r, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        findByCategory(r, cb);
        findByName(r, cb);
        findByPrice(r, cb);

        return cb.and(predicates.toArray(new Predicate[0]));
    }

    private void findByName(Root<Product> r, CriteriaBuilder cb) {
        if (filter.getName() != null) {
            predicates.add(cb.like(r.get("name"), filter.getName()));
        }
    }

    private void findByPrice(Root<Product> r, CriteriaBuilder cb) {
        if (filter.getMinPrice() != null && filter.getMaxPrice() != null) {
            predicates.add(cb.between(r.get("price"), filter.getMinPrice(), filter.getMaxPrice()));
        }
    }

    private void findByCategory(Root<Product> r, CriteriaBuilder cb) {
//        if (filter.getCategoryId() == null) return;

//        Join<Product, Category> category = r.join("category");
//        predicates.add(cb.equal(category.get("id"), filter.getCategoryId()));

        if (filter.getCategoriesId().isEmpty()) return;

        Join<Product, Category> category = r.join("category");
        predicates.add(category.get("id").in(filter.getCategoriesId()));
    }
}
