package homes.has.api;

import homes.has.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class TestController {


    private final FileService fileService;


    @GetMapping("/upload")
    public String testUploadForm() {

        return "test/uploadTest";
    }

    @PostMapping("/upload")
    public void uploadFile(@RequestPart("files") List<MultipartFile> files) throws IOException {

        for (MultipartFile multipartFile : files) {
            fileService.saveFile(multipartFile);
        }

    }
}
