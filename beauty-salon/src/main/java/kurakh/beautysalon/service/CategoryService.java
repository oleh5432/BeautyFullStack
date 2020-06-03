package kurakh.beautysalon.service;

import kurakh.beautysalon.dto.request.CategoryFilterRequest;
import kurakh.beautysalon.dto.request.CategoryRequest;
import kurakh.beautysalon.dto.response.CategoryResponse;
import kurakh.beautysalon.dto.response.DataResponse;
import kurakh.beautysalon.entity.Category;
import kurakh.beautysalon.exception.WrongInputDataException;
import kurakh.beautysalon.repository.CategoryRepository;
import kurakh.beautysalon.specification.CategorySpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private FileService fileService;

    @Autowired
    private SectionService sectionService;

    public CategoryResponse save(CategoryRequest categoryRequest) throws WrongInputDataException, IOException {
        return new CategoryResponse(categoryRepository.save(categoryRequestToCategory(categoryRequest, null)));
    }

    public List<CategoryResponse> findAll() {
        return categoryRepository.findAll().stream()
                .map(CategoryResponse::new)
                .collect(Collectors.toList());
    }

    public CategoryResponse update(Long id, CategoryRequest categoryRequest) throws WrongInputDataException, IOException {
        return new CategoryResponse(categoryRepository
                .save(categoryRequestToCategory(categoryRequest, findOneById(id))));
    }

    private Category categoryRequestToCategory(CategoryRequest categoryRequest, Category category) throws WrongInputDataException, IOException {
        if (category == null) {
            category = new Category();
        }
        category.setName(categoryRequest.getName());
        String file = fileService.saveFile(categoryRequest.getFileRequest());
        category.setPathToImg(file);
        category.setSection(sectionService.findOneById(categoryRequest.getSectionId()));
        return category;
    }

    public void delete(Long id) throws WrongInputDataException {
        Category category = findOneById(id);
        if (!category.getProducts().isEmpty()) {
            throw new WrongInputDataException("Category with id " + id + " has products (has dependencies)");
        }
        categoryRepository.delete(category);
    }

//    public DataResponse<CategoryResponse> findAll(Integer page, Integer size, String fieldName, Sort.Direction direction, String name) {
//        if (direction == null) {
//            direction = Sort.Direction.ASC;
//        }
//        if (fieldName == null) {
//            fieldName = "id";
//        }
//
//        Page<Category> categoriesPage = name == null ?
//                categoryRepository.findAll(PageRequest.of(page, size, direction, fieldName)) :
//                categoryRepository.findAllByNameLike(PageRequest.of(page, size, direction, fieldName), '%' + name + '%');
//
//        return new DataResponse<>(
//                categoriesPage.get().map(CategoryResponse::new).collect(Collectors.toList()),
//                categoriesPage.getTotalPages(),
//                categoriesPage.getTotalElements()
//        );
//    }

    public DataResponse<CategoryResponse> findAll(Integer page, Integer size, String fieldName, Sort.Direction direction, String name, List<Long> sectionsId) {
        if (direction == null) {
            direction = Sort.Direction.ASC;
        }
        if (fieldName == null) {
            fieldName = "id";
        }

        Page<Category> categoriesPage = sectionsId == null ?
                categoryRepository.findAll(PageRequest.of(page, size, direction, fieldName)) :
                categoryRepository.findAllBySection_Id(PageRequest.of(page, size, direction, fieldName), sectionsId)
//        :
//                categoryRepository.findAllByNameLike(PageRequest.of(page, size, direction, fieldName), '%' + name + '%')
                ;

        return new DataResponse<>(
                categoriesPage.get().map(CategoryResponse::new).collect(Collectors.toList()),
                categoriesPage.getTotalPages(),
                categoriesPage.getTotalElements()
        );
    }

    public DataResponse<CategoryResponse> findAll(Integer pageArg, Integer size, String fieldName, Sort.Direction direction, CategoryFilterRequest filterRequest) {
        Page<Category> page = categoryRepository.findAll(new CategorySpecification(filterRequest), PageRequest.of(pageArg, size, direction, fieldName));
        return new DataResponse<>(page.get().map(CategoryResponse::new).collect(Collectors.toList()),
                page.getTotalPages(),
                page.getTotalElements());
    }

    public Category findOneByName(String name) throws WrongInputDataException {
        return categoryRepository.findByName(name)
                .orElseThrow(() -> new WrongInputDataException("Category with name '" + name + "' not exists"));
    }

    public Category findOneById(Long id) throws WrongInputDataException {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new WrongInputDataException("Category with id '" + id + "' not exists"));
    }

    public CategoryResponse findById(Long id) throws WrongInputDataException {
        return new CategoryResponse(findOneById(id));
    }
}
