package kurakh.beautysalon.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Getter
@Setter
public class PaginationRequest {

    private Integer page;

    private Integer size;

    private Sort.Direction direction;

    private String fieldName;

    public Pageable toPageable() {
        return PageRequest.of(page, size, direction, fieldName);
    }

}
