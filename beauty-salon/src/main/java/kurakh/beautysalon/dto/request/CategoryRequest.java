package kurakh.beautysalon.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class CategoryRequest {

    @NotBlank
    @Size(min = 2)
    private String name;

    private FileRequest fileRequest;

    @NotNull
    private Long sectionId;
}
