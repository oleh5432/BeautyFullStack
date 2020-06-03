package kurakh.beautysalon.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CategoryFilterRequest {

    private PaginationRequest paginationRequest;

    private String name;

    private List<Long> sectionsId = new ArrayList<>();

    public CategoryFilterRequest(String name, List<Long> sectionsId) {
        this.name = name;
        this.sectionsId = sectionsId;
    }
}