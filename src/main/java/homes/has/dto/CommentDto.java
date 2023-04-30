package homes.has.dto;

import homes.has.domain.Comment;
import homes.has.domain.Post;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;


@Getter
public class CommentDto {
    private Long id;
    private String body;
    private int likes;
    private Long postId;
    private Long memberId;
    private Long parentId;
    private List<Comment> children;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;

    @Builder
    public CommentDto(Long id, String body, int likes, Long postId, Long memberId,Long parentId
            ,List<Comment> children ,LocalDateTime createAt, LocalDateTime modifiedAt) {
        this.id= id;
        this.body = body;
        this.likes = likes;
        this.postId= postId;
        this.memberId = memberId;
        this.parentId=parentId;
        this.children = children;
        this.createAt= createAt;
        this.modifiedAt=modifiedAt;
    }

}
