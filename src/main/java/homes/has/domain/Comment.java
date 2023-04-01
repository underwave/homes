package homes.has.domain;


import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Entity
public class Comment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String body;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    private int likes;

//   게시글
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name= "post_id")
    private Post post;


    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name= "user_id")
    private Member member;

// 대댓글 자기참조
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent")
    private List<Comment> children;




}
