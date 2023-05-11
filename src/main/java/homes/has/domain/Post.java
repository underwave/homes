package homes.has.domain;


import homes.has.domain.timestamp.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

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


    @Builder
    public Post(Member member, String title, String body, Category category, String imageUrl){
        this.member = member;
        this.title= title;
        this.body= body;
        this.category = category;
        this.imageUrl= imageUrl;
        this.likes=0;
        this.comments = 0;
    }

    public void update(String title, String body, String imageUrl){

    }

}
