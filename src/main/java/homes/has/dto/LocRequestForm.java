package homes.has.dto;


import homes.has.domain.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LocRequestForm {

    private Member member;
    private String location;
    private String imageUrl;

    @Builder
    public LocRequestForm(Member member, String location, String imageUrl){
        this.member=member;
        this.location = location;
        this.imageUrl=imageUrl;
    }
}
