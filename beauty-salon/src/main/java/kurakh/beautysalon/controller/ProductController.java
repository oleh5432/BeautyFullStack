package kurakh.beautysalon.controller;

import kurakh.beautysalon.dto.request.FileRequest;
import kurakh.beautysalon.dto.request.ProductFilterRequest;
import kurakh.beautysalon.dto.request.ProductRequest;
import kurakh.beautysalon.dto.response.DataResponse;
import kurakh.beautysalon.dto.response.ProductResponse;
import kurakh.beautysalon.exception.WrongInputDataException;
import kurakh.beautysalon.service.FileService;
import kurakh.beautysalon.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public String upload(@RequestBody FileRequest fileRequest) throws IOException {
        String filePath = fileService.saveFile(fileRequest);
        return filePath;
    }

    @PostMapping
    public ProductResponse save(@RequestBody @Valid ProductRequest productRequest) throws WrongInputDataException, IOException {
        return productService.save(productRequest);
    }

    @GetMapping
    public List<ProductResponse> findAll() {
        return productService.findAll();
    }

    @GetMapping("/findById")
    public ProductResponse findById(@RequestParam @Valid Long id) throws WrongInputDataException {
        return productService.findById(id);
    }

    @PostMapping("/page")
    public DataResponse<ProductResponse> findAll(@RequestBody ProductFilterRequest productFilterRequest) {
        return productService.findAll(productFilterRequest);
    }

    @GetMapping("/page/filter")
    public DataResponse<ProductResponse> findAll(@RequestParam Integer page,
                                                 @RequestParam Integer size,
                                                 @RequestParam(required = false) Sort.Direction direction,
                                                 @RequestParam(required = false) String fieldName,
                                                 @RequestParam(required = false) String name,
                                                 @RequestParam(required = false) Integer minTimeMinutes,
                                                 @RequestParam(required = false) Integer maxTimeMinutes,
//                                                 @RequestParam(required = false) LocalDateTime startTime,
                                                 @RequestParam(required = false) Integer minPrice,
                                                 @RequestParam(required = false) Integer maxPrice,
                                                 @RequestParam(required = false) List<Long> categoriesId) {
        return productService.findAll(
                page, size, fieldName, direction, new ProductFilterRequest(name, minTimeMinutes, maxTimeMinutes,
        //                        startTime,
                                minPrice, maxPrice, categoriesId));
    }

    @GetMapping("/page")
    public DataResponse<ProductResponse> findAll(@RequestParam Integer page,
                                                 @RequestParam Integer size,
                                                 @RequestParam(required = false) Sort.Direction direction,
                                                 @RequestParam(required = false) String fieldName,
                                                 @RequestParam(required = false) String name,
                                                 @RequestParam(required = false) List<Long> categoriesId
    ) {
        return productService.findAll(page, size, fieldName, direction, name, categoriesId);
    }

    @PutMapping
    public ProductResponse update(@RequestParam @Valid Long id,
                              @RequestBody ProductRequest productRequest) throws WrongInputDataException, IOException {
        return productService.update(id, productRequest);
    }

    @DeleteMapping
    public void delete(@RequestParam Long id) throws WrongInputDataException{
        productService.delete(id);
    }
}
