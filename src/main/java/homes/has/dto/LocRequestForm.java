package homes.has.dto;


import homes.has.domain.ImageFile;
import homes.has.domain.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LocRequestForm {

    private Member member;
    private String location;
    private ImageFile imageFile;

    @Builder
    public LocRequestForm(Member member, String location, ImageFile imageFile){
        this.member=member;
        this.location = location;
        this.imageFile= imageFile;
    }
}
