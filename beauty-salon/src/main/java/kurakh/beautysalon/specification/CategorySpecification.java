package kurakh.beautysalon.specification;

import kurakh.beautysalon.dto.request.CategoryFilterRequest;
import kurakh.beautysalon.entity.Category;
import kurakh.beautysalon.entity.Section;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class CategorySpecification implements Specification<Category>{

    private CategoryFilterRequest filter;

    private List<Predicate> predicates = new ArrayList<>();

    public CategorySpecification(CategoryFilterRequest filterRequest) {
            this.filter = filter;
    }

    @Override
    public Predicate toPredicate(Root<Category> r, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        findBySection(r, cb);
        findByName(r, cb);

        return cb.and(predicates.toArray(new Predicate[0]));
    }

    private void findBySection(Root<Category> r, CriteriaBuilder cb) {
        if (filter.getSectionsId().isEmpty()) return;

        Join<Category, Section> category = r.join("section");
        predicates.add(category.get("id").in(filter.getSectionsId()));

    }

    private void findByName(Root<Category> r, CriteriaBuilder cb) {
        if (filter.getName() != null) {
            predicates.add(cb.like(r.get("name"), filter.getName()));
        }
    }
}
