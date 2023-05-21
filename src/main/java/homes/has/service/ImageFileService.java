package homes.has.service;



import homes.has.domain.ImageFile;
import homes.has.repository.ImageFileRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional
public class ImageFileService {

    @Value("${file.dir}")
    private String fileDir;

    private final ImageFileRepository imageFileRepository;

    public Long saveFile(MultipartFile files) throws IOException {
        if (files.isEmpty()) {
            return null;
        }

        // 원래 파일 이름 추출
        String originalFilename = files.getOriginalFilename();

        // 파일 이름으로 쓸 uuid 생성
        String uuid = UUID.randomUUID().toString();

        // 확장자 추출(ex : .png)
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

        // uuid와 확장자 결합
        String savedName = uuid + extension;

        // 파일을 불러올 때 사용할 파일 경로
        String path = fileDir + savedName;


        // 파일 엔티티 생성
        ImageFile imageFile = ImageFile.builder()
                .originalFileName(originalFilename)
                .savedName(savedName)
                .path(path)
                .build();

        File file = new File(path);

        if(!file.exists()){
            // mkdir() 함수와 다른 점은 상위 디렉토리가 존재하지 않을 때 그것까지 생성
            file.mkdirs();
        }


        // 실제로 로컬에 uuid를 파일명으로 저장
        files.transferTo(file);

        // 데이터베이스에 파일 정보 저장
        ImageFile save = imageFileRepository.save(imageFile);
        return save.getId();
    }


}
