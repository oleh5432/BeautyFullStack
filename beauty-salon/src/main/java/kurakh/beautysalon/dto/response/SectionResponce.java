package kurakh.beautysalon.dto.response;

import kurakh.beautysalon.entity.Section;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SectionResponce {
    private Long id;

    private String name;

    private String pathToImage;

    public SectionResponce(Section section) {
        id = section.getId();
        name = section.getName();
        pathToImage = section.getPathToImg();
    }
}

