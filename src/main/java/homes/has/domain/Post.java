package homes.has.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import homes.has.domain.timestamp.BaseEntity;
import homes.has.enums.Category;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String body;

    @Enumerated(EnumType.STRING)
    private Category category;

    private int likes;

    private int comments;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @JsonIgnore
    @OneToMany(mappedBy = "post")
    private List<PostImageFile> postImageFiles = new ArrayList<>();


    @Builder
    public Post(Member member, String title, String body, Category category, List<PostImageFile> postImageFiles){
        this.member = member;
        this.title= title;
        this.body= body;
        this.category = category;
        this.postImageFiles= postImageFiles;
        this.likes=0;
        this.comments = 0;
    }

    public void update(String title, String body, List<PostImageFile> postImageFiles){
        this.title= title;
        this.body = body;
        this.postImageFiles=postImageFiles;
    }

}
