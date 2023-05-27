package homes.has.dto;

import homes.has.domain.Member;
import homes.has.domain.ReviewBody;
import homes.has.domain.ReviewGrade;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CreateReviewDto{
    private UUID memberId;
    private String location;
    private ReviewGrade grade;
    private ReviewBody body;
    private Double posx;
    private Double posy;
    private List<MultipartFile> files;

}
