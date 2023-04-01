package homes.has.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter @Setter
@Entity
@Table(name="Request_location")
public class LocRequest {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageURL;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;


    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name= "member_id")
    private Member member;

}
