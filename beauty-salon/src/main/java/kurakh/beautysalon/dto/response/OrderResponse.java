package kurakh.beautysalon.dto.response;

import kurakh.beautysalon.entity.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class OrderResponse {
    private Long id;

    private List<ProductResponse> productResponseList;

    private String userName;

    public OrderResponse(Order order) {
        id = order.getId();
        productResponseList = order.getProducts().stream().map(ProductResponse::new).collect(Collectors.toList());
        userName = order.getUser().getName();
    }
}
