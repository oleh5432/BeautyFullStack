package kurakh.beautysalon.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ProductFilterRequest {

    private PaginationRequest paginationRequest;

    private String name;

    @Positive
    private Integer minTimeMinutes = 0;

    private Integer maxTimeMinutes = Integer.MAX_VALUE;

//    private LocalDateTime startTime;

    @Positive
    private Integer minPrice  = 0;

    private Integer maxPrice = Integer.MAX_VALUE;

    private List<Long> categoriesId = new ArrayList<>();

    public ProductFilterRequest(String name, Integer minTimeMinutes, Integer maxTimeMinutes,
//                                LocalDateTime startTime,
                                Integer minPrice, Integer maxPrice, List<Long> categoriesId) {
        this.name = name;
        this.minTimeMinutes = minTimeMinutes;
        this.maxTimeMinutes = maxTimeMinutes;
//        this.startTime = startTime;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.categoriesId = categoriesId;
    }
}
