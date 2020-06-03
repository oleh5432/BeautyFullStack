package kurakh.beautysalon.dto.response;

import kurakh.beautysalon.entity.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryResponse {
    private Long id;

    private String name;

    private String pathToImage;

    private String sectionName;

    public CategoryResponse(Category category) {
        id = category.getId();
        name = category.getName();
        pathToImage = category.getPathToImg();
        if(category.getSection() != null)
            sectionName = category.getSection().getName();
    }
}
