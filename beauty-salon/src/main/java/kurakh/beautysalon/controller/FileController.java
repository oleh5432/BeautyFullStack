package kurakh.beautysalon.controller;

import kurakh.beautysalon.dto.request.FileRequest;
import kurakh.beautysalon.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

//@CrossOrigin
@RestController
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public String upload(@RequestBody FileRequest fileRequest) throws IOException {
        String filePath = fileService.saveFile(fileRequest);
        return filePath;
    }
}
