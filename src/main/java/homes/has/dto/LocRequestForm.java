package homes.has.dto;


import homes.has.domain.ImageFile;
import homes.has.domain.Member;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class LocRequestForm {

    private Member member;
    private String location;
    private ImageFile imageFile;
    private LocalDateTime createdAt;

    @Builder
    public LocRequestForm(Member member, String location, ImageFile imageFile, LocalDateTime createdAt){
        this.member=member;
        this.location = location;
        this.imageFile= imageFile;
        this.createdAt= createdAt;
    }
}
