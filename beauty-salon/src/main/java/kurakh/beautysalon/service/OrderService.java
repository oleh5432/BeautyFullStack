package kurakh.beautysalon.service;

import kurakh.beautysalon.dto.request.OrderRequest;
import kurakh.beautysalon.dto.response.DataResponse;
import kurakh.beautysalon.dto.response.OrderResponse;
import kurakh.beautysalon.entity.Order;
import kurakh.beautysalon.exception.WrongInputDataException;
import kurakh.beautysalon.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    public OrderResponse save(OrderRequest orderRequest) throws WrongInputDataException {
        return new OrderResponse(orderRepository.save(orderRequestToOrder(orderRequest, null)));
    }

    public OrderResponse update(Long id, OrderRequest orderRequest) throws WrongInputDataException {
        return new OrderResponse(orderRepository
                .save(orderRequestToOrder(orderRequest, findOneById(id))));
    }

    public List<OrderResponse> findAll() {
        return orderRepository.findAll().stream()
                .map(OrderResponse::new)
                .collect(Collectors.toList());
    }

    public DataResponse<OrderResponse> findAll(Integer page, Integer size, String fieldName, Sort.Direction direction) {
        if (direction == null) {
            direction = Sort.Direction.ASC;
        }
        if (fieldName == null) {
            fieldName = "id";
        }

        Page<Order> categoriesPage = orderRepository.findAll(PageRequest.of(page, size, direction, fieldName));

        return new DataResponse<>(
                categoriesPage.get().map(OrderResponse::new).collect(Collectors.toList()),
                categoriesPage.getTotalPages(),
                categoriesPage.getTotalElements()
        );
    }

    public void delete(Long id) throws WrongInputDataException {
        Order order = findOneById(id);
        if (!order.getProducts().isEmpty()) {
            throw new WrongInputDataException("Category with id " + id + " has products (has dependencies)");
        }
        orderRepository.delete(order);
    }

    private Order orderRequestToOrder(OrderRequest orderRequest, Order order) throws WrongInputDataException {
        if (order == null) {
            order = new Order();
        }
        order.setProducts(productService.findProductsById(orderRequest.getProductId()));
        order.setUser(userService.findOneById(orderRequest.getUserId()));
        return order;
    }



    public Order findOneById(Long id) throws WrongInputDataException {
        return orderRepository.findById(id)
                .orElseThrow(() -> new WrongInputDataException("Product with id '" + id + "' not exists"));

    }
}
