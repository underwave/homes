package homes.has.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@Entity
public class Review {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "member_id")
    private Member member;

    private String Location;

    private String imageURL;

    private LocalDateTime createAt;

    private LocalDateTime modifiedAt;

    @Embedded
    private ReviewBody body;

    @Embedded
    private ReviewGrade grade;



}
