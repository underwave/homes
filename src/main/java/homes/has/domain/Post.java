package homes.has.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@Entity
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String body;

    @Enumerated(EnumType.STRING)
    private Category category;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private String imageUrl;

    private int likes;

    private int comment;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

}
