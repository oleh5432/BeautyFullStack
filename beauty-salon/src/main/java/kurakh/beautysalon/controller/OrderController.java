package kurakh.beautysalon.controller;

import kurakh.beautysalon.dto.request.OrderRequest;
import kurakh.beautysalon.dto.response.DataResponse;
import kurakh.beautysalon.dto.response.OrderResponse;
import kurakh.beautysalon.exception.WrongInputDataException;
import kurakh.beautysalon.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public OrderResponse save(@RequestBody OrderRequest orderRequest) throws WrongInputDataException {
        return orderService.save(orderRequest);
    }

    @GetMapping
    public List<OrderResponse> findAll() {
        return orderService.findAll();
    }


    @GetMapping("/page")
    public DataResponse<OrderResponse> findAll(@RequestParam Integer page,
                                                  @RequestParam Integer size,
                                                  @RequestParam(required = false) Sort.Direction direction,
                                                  @RequestParam(required = false) String fieldName
    ) {
        return orderService.findAll(page, size, fieldName, direction);
    }

    @PutMapping
    public OrderResponse update(@RequestParam Long id,
                                   @RequestBody OrderRequest orderRequest) throws WrongInputDataException {
        return orderService.update(id, orderRequest);
    }

    @DeleteMapping
    public void delete(@RequestParam Long id) throws WrongInputDataException {
        orderService.delete(id);
    }
}
