package homes.has.api;

import homes.has.domain.Post;
import homes.has.service.FileService;
import homes.has.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@Slf4j
public class TestController {
    private final PostService postService;

    @Value("${file.dir}")
    private String fileDir;


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

// 출력 성공
    @GetMapping("/display/{postId}")
    public ResponseEntity<byte[]> getFile(@PathVariable Long postId) {
        ResponseEntity<byte[]> result = null;

        Post post = postService.findById(postId).get();
        String path = post.getImageUrl();

        try {
//            String srcFileName = URLDecoder.decode(fileName, "UTF-8");
//            log.info("filename : " + srcFileName);
            File file = new File(path);
            log.info("file : " + file);
            HttpHeaders header = new HttpHeaders();

            //MIME 타입 처리
            header.add("Content-Type", Files.probeContentType(file.toPath()));
            //File객체를 Path로 변환하여 MIME 타입을 판단하여 HTTPHeaders의 Content-Type에  값으로 들어갑니다.

            //파일 데이터 처리 *FileCopyUtils.copy 아래에 정리
            //new ResponseEntity(body,header,status)
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);

        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }
}
