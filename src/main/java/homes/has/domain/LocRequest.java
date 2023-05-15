package homes.has.domain;


import homes.has.domain.timestamp.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter @Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="Request_location")
public class LocRequest extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String location;
    private String imageUrl;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name= "member_id")
    private Member member;

    @Builder
    public LocRequest(String location, String imageUrl, Member member){
        this.imageUrl=imageUrl;
        this.location=location;
        this.member=member;
    }
}
