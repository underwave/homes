package homes.has.dto;

import homes.has.domain.Building;
import homes.has.domain.Member;
import homes.has.domain.ReviewBody;
import homes.has.domain.ReviewGrade;
import homes.has.domain.timestamp.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;


@Getter
public class ReviewDto {
    private Long id;
    private String memberId;
    private String location;
    private ReviewGrade grade;
    private ReviewBody body;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Building building;
    private List<MultipartFile> files;
    private List<ResponseEntity<byte[]>> images;

    @Builder
    public ReviewDto(String memberId, Long id, String location, ReviewGrade grade , ReviewBody body,
                     Building building, LocalDateTime createdAt, LocalDateTime modifiedAt,
                     List<MultipartFile> files, List<ResponseEntity<byte[]>> images){
        this.memberId=memberId;
        this.body=body;
        this.location=location;
        this.grade=grade;
        this.createdAt =createdAt;
        this.modifiedAt= modifiedAt;
        this.building=building;
        this.id=id;
        this.files=files;
        this.images = images;
    }

}
