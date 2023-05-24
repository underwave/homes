package homes.has.dto;

import homes.has.domain.Building;
import homes.has.domain.Member;
import homes.has.domain.ReviewBody;
import homes.has.domain.ReviewGrade;
import homes.has.domain.timestamp.BaseEntity;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReviewDto {
    private Long id;
    private Member member;
    private String location;
    private ReviewGrade grade;
    private ReviewBody body;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Building building;
    @Builder
    public ReviewDto(Member member,Long id, String location, ReviewGrade grade , ReviewBody body,
                     Building building, LocalDateTime createdAt, LocalDateTime modifiedAt){
        this.member=member;
        this.body=body;
        this.location=location;
        this.grade=grade;
        this.createdAt =createdAt;
        this.modifiedAt= modifiedAt;
        this.building=building;
        this.id=id;
    }

}
