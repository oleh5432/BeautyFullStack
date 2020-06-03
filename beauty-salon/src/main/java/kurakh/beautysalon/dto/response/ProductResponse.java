package kurakh.beautysalon.dto.response;

import kurakh.beautysalon.entity.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductResponse {
    private Long id;

    private String name;

    private Integer timeMinutes;

//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
//    private LocalDateTime startTime;

    private Integer price;

    private String categoryName;

    private String pathToImage;

    public ProductResponse(Product product) {
        id = product.getId();
        name = product.getName();
        timeMinutes = product.getTimeMinutes();
//        startTime = product.getStartTime();
        price = product.getPrice();
        if(product.getCategory() != null)
            categoryName = product.getCategory().getName();
        pathToImage = product.getPathToImg();
    }
}
