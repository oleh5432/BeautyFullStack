package kurakh.beautysalon.service;

import kurakh.beautysalon.dto.request.ProductFilterRequest;
import kurakh.beautysalon.dto.request.ProductRequest;
import kurakh.beautysalon.dto.response.DataResponse;
import kurakh.beautysalon.dto.response.ProductResponse;
import kurakh.beautysalon.entity.Product;
import kurakh.beautysalon.exception.WrongInputDataException;
import kurakh.beautysalon.repository.ProductRepository;
import kurakh.beautysalon.specification.ProductSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private FileService fileService;

    public ProductResponse save(ProductRequest productRequest) throws WrongInputDataException, IOException {
        return new ProductResponse(productRepository.save(productRequestToProduct(productRequest, null)));
    }

    public ProductResponse update(Long id, ProductRequest productRequest) throws WrongInputDataException, IOException {
        return new ProductResponse(productRepository
                .save(productRequestToProduct(productRequest, findOneById(id))));
    }

    private Product productRequestToProduct(ProductRequest productRequest, Product product) throws WrongInputDataException, IOException {
        if (product == null) {
            product = new Product();
        }
        product.setPrice(productRequest.getPrice());
        product.setName(productRequest.getName());
        product.setTimeMinutes(productRequest.getTimeMinutes());
//        product.setStartTime(productRequest.getStartTime());
        product.setCategory(categoryService.findOneById(productRequest.getCategoryId()));
        String file = fileService.saveFile(productRequest.getFileRequest());
        product.setPathToImg(file);
        return product;
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    public List<ProductResponse> findAll() {
        return productRepository.findAll().stream()
                .map(ProductResponse::new)
                .collect(Collectors.toList());
    }

    public List<Product> findProductsById(List<Long> productsId) {
        return productRepository.findProductsById(productsId);
    }

    public DataResponse<ProductResponse> findAll(Integer page, Integer size, String fieldName, Sort.Direction direction, String name, List<Long> categoriesId) {
        if (direction == null) {
            direction = Sort.Direction.ASC;
        }
        if (fieldName == null) {
            fieldName = "id";
        }

        Page<Product> productPage = name == null ?
//                productRepository.findAll(PageRequest.of(page, size, direction, fieldName)) :
                productRepository.findAll(PageRequest.of(page, size, direction, fieldName)) :
                productRepository.findAllByNameLike(PageRequest.of(page, size, direction, fieldName), '%' + name + '%');

        return new DataResponse<>(
                productPage.get().map(ProductResponse::new).collect(Collectors.toList()),
                productPage.getTotalPages(),
                productPage.getTotalElements()
        );
    }

    public DataResponse<ProductResponse> findAll(ProductFilterRequest filterRequest) {
        Page<Product> page = productRepository.findAll(new ProductSpecification(filterRequest), filterRequest.getPaginationRequest().toPageable());
        return new DataResponse<>(page.get().map(ProductResponse::new).collect(Collectors.toList()),
                page.getTotalPages(),
                page.getTotalElements());
    }

    public DataResponse<ProductResponse> findAll(Integer pageArg, Integer size, String fieldName, Sort.Direction direction, ProductFilterRequest filterRequest) {
        Page<Product> page = productRepository.findAll(new ProductSpecification(filterRequest), PageRequest.of(pageArg, size, direction, fieldName));
        return new DataResponse<>(page.get().map(ProductResponse::new).collect(Collectors.toList()),
                page.getTotalPages(),
                page.getTotalElements());
    }

    public Product findOneById(Long id) throws WrongInputDataException {
        return productRepository.findById(id)
                .orElseThrow(() -> new WrongInputDataException("Product with id '" + id + "' not exists"));

    }

    public ProductResponse findById(Long id) throws WrongInputDataException {
        return new ProductResponse(findOneById(id));
    }
}
