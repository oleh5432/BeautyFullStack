package kurakh.beautysalon.controller;

import kurakh.beautysalon.dto.request.CategoryRequest;
import kurakh.beautysalon.dto.response.CategoryResponse;
import kurakh.beautysalon.dto.response.DataResponse;
import kurakh.beautysalon.exception.WrongInputDataException;
import kurakh.beautysalon.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public CategoryResponse save(@RequestBody @Valid CategoryRequest categoryRequest) throws WrongInputDataException, IOException {
        return categoryService.save(categoryRequest);
    }

    @GetMapping
    public List<CategoryResponse> findAll() {
        return categoryService.findAll();

    }

    @GetMapping("/findById")
    public CategoryResponse findById(@RequestParam @Valid Long id) throws WrongInputDataException {
        return categoryService.findById(id);
    }

//    @GetMapping("/page/filter")
//    public DataResponse<CategoryResponse> findAll(@RequestParam Integer page,
//                                                 @RequestParam Integer size,
//                                                 @RequestParam(required = false) Sort.Direction direction,
//                                                 @RequestParam(required = false) String fieldName,
//                                                 @RequestParam(required = false) String name,
//                                                 @RequestParam(required = false) List<Long> sectionsId) {
//        return categoryService.findAll(page, size, fieldName, direction, new CategoryFilterRequest(name, sectionsId));
//    }

//    @GetMapping("/page")
//    public DataResponse<CategoryResponse> findAll(@RequestParam Integer page,
//                                                  @RequestParam Integer size,
//                                                  @RequestParam(required = false) Sort.Direction direction,
//                                                  @RequestParam(required = false) String fieldName,
//                                                  @RequestParam(required = false) String name
//    ) {
//        return categoryService.findAll(page, size, fieldName, direction, name);
//    }

    @GetMapping("/page")
    public DataResponse<CategoryResponse> findAll(@RequestParam Integer page,
                                                  @RequestParam Integer size,
                                                  @RequestParam(required = false) Sort.Direction direction,
                                                  @RequestParam(required = false) String fieldName,
                                                  @RequestParam(required = false) String name,
                                                  @RequestParam(required = false) List<Long> sectionsId
    ) {
        return categoryService.findAll(page, size, fieldName, direction, name, sectionsId);
    }

    @PutMapping
    public CategoryResponse update(@RequestParam @Valid Long id,
                                  @RequestBody CategoryRequest categoryRequest) throws WrongInputDataException, IOException {
        return categoryService.update(id, categoryRequest);
    }

    @DeleteMapping
    public void delete(@RequestParam Long id) throws WrongInputDataException {
        categoryService.delete(id);
    }
}
