package homes.has.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "member_id")
    private Member member;

    @ManyToOne(fetch=FetchType.LAZY )
    @JoinColumn(name= "building_id")
    private Building building;

    private String Location;

    private String imageURL;

    private LocalDateTime createAt;

    private LocalDateTime modifiedAt;

    @Embedded
    private ReviewBody body;

    @Embedded
    private ReviewGrade grade;


}
