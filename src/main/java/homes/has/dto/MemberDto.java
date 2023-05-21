package homes.has.dto;


import homes.has.domain.Valid;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberDto {

    private Long id;
    private Valid valid;
    private String location;
    private String name;
    private String nickName;
    private String accessToken;

    private String provideId;


    @Builder
    public MemberDto(Long id, Valid valid, String location, String name, String accessToken, String provideId, String nickName){
        this.id =id;
        this.valid=valid;
        this.location=location;
        this.name= name;
        this.accessToken= accessToken;
        this.provideId=provideId;
        this.nickName=nickName;
    }
}
