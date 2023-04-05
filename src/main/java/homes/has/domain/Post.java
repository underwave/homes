package homes.has.domain;


import homes.has.domain.timestamp.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String body;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String imageUrl;

    private int likes;

    private int comments;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;


    public Post(Member member, String title, String body, Category category){
        this.member = member;
        this.title= title;
        this.body= body;
        this.category = category;

        this.likes=0;
        this.comments = 0;
    }

}
