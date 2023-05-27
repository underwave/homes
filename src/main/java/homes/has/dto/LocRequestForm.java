package homes.has.dto;


import homes.has.domain.ImageFile;
import homes.has.domain.Member;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class LocRequestForm {

    private Long memberId;
    private String location;
    private MultipartFile file;
    private LocalDateTime createdAt;

    @Builder
    public LocRequestForm(Long memberId, String location, MultipartFile file, LocalDateTime createdAt){
        this.memberId=memberId;
        this.location = location;
        this.file= file;
        this.createdAt= createdAt;
    }
}
