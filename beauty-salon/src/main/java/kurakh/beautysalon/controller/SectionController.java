package kurakh.beautysalon.controller;

import kurakh.beautysalon.dto.request.SectionRequest;
import kurakh.beautysalon.dto.response.DataResponse;
import kurakh.beautysalon.dto.response.SectionResponce;
import kurakh.beautysalon.exception.WrongInputDataException;
import kurakh.beautysalon.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/section")
public class SectionController {

    @Autowired
    private SectionService sectionService;

    @PostMapping
    public SectionResponce save(@RequestBody @Valid SectionRequest sectionRequest) throws WrongInputDataException, IOException {
        return sectionService.save(sectionRequest);
    }

    @GetMapping
    public List<SectionResponce> findAll() {
        return sectionService.findAll();

    }

    @GetMapping("/findById")
    public SectionResponce findById(@RequestParam @Valid Long id) throws WrongInputDataException {
        return sectionService.findById(id);
    }

    @GetMapping("/page")
    public DataResponse<SectionResponce> findAll(@RequestParam Integer page,
                                                  @RequestParam Integer size,
                                                  @RequestParam(required = false) Sort.Direction direction,
                                                  @RequestParam(required = false) String fieldName,
                                                  @RequestParam(required = false) String name
    ) {
        return sectionService.findAll(page, size, fieldName, direction, name);
    }

    @PutMapping
    public SectionResponce update(@RequestParam @Valid Long id,
                                  @RequestBody SectionRequest sectionRequest) throws WrongInputDataException, IOException {
        return sectionService.update(id, sectionRequest);
    }

    @DeleteMapping
    public void delete(@RequestParam Long id) throws WrongInputDataException {
        sectionService.delete(id);
    }
}
