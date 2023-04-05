package homes.has.domain;


import homes.has.domain.timestamp.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

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
