package kurakh.beautysalon.service;

import kurakh.beautysalon.dto.request.SectionRequest;
import kurakh.beautysalon.dto.response.DataResponse;
import kurakh.beautysalon.dto.response.SectionResponce;
import kurakh.beautysalon.entity.Section;
import kurakh.beautysalon.exception.WrongInputDataException;
import kurakh.beautysalon.repository.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SectionService {

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private FileService fileService;


    public SectionResponce save(SectionRequest sectionRequest) throws WrongInputDataException, IOException {
        return new SectionResponce(sectionRepository.save(sectionRequestToSection(sectionRequest, null)));
    }

    public List<SectionResponce> findAll() {
        return sectionRepository.findAll().stream()
                .map(SectionResponce::new)
                .collect(Collectors.toList());
    }

    public SectionResponce update(Long id, SectionRequest sectionRequest) throws WrongInputDataException, IOException {
        return new SectionResponce(sectionRepository
                .save(sectionRequestToSection(sectionRequest, findOneById(id))));
    }

    private Section sectionRequestToSection(SectionRequest sectionRequest, Section section) throws WrongInputDataException, IOException {
        if (section == null) {
            section = new Section();
        }
        section.setName(sectionRequest.getName());
        String file = fileService.saveFile(sectionRequest.getFileRequest());
        section.setPathToImg(file);
//        category.setSection(serviceService.findOneById(serviceRequest.getServiceId()));
        return section;
    }

    public void delete(Long id) throws WrongInputDataException {
        Section section = findOneById(id);
        if (!section.getCategories().isEmpty()) {
            throw new WrongInputDataException("Section with id " + id + " has categories (has dependencies)");
        }
        sectionRepository.delete(section);
    }

    public DataResponse<SectionResponce> findAll(Integer page, Integer size, String fieldName, Sort.Direction direction, String name) {
        if (direction == null) {
            direction = Sort.Direction.ASC;
        }
        if (fieldName == null) {
            fieldName = "id";
        }

        Page<Section> sectionsPage = name == null ?
                sectionRepository.findAll(PageRequest.of(page, size, direction, fieldName)) :
                sectionRepository.findAllByNameLike(PageRequest.of(page, size, direction, fieldName), '%' + name + '%');

        return new DataResponse<>(
                sectionsPage.get().map(SectionResponce::new).collect(Collectors.toList()),
                sectionsPage.getTotalPages(),
                sectionsPage.getTotalElements()
        );
    }

    public Section findOneByName(String name) throws WrongInputDataException {
        return sectionRepository.findByName(name)
                .orElseThrow(() -> new WrongInputDataException("Category with name '" + name + "' not exists"));
    }

    public Section findOneById(Long id) throws WrongInputDataException {
        return sectionRepository.findById(id)
                .orElseThrow(() -> new WrongInputDataException("Category with id '" + id + "' not exists"));
    }

    public SectionResponce findById(Long id) throws WrongInputDataException {
        return new SectionResponce(findOneById(id));
    }
}
