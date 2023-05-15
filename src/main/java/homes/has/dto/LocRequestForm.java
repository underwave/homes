package homes.has.dto;


import lombok.Getter;

@Getter
public class LocRequestForm {

    private Long memberId;
    private String location;
    private String imageUrl;

    public LocRequestForm(Long memberId, String location, String imageUrl){
        this.memberId=memberId;
        this.location = location;
        this.imageUrl=imageUrl;
    }
}
