package homes.has.dto;

import homes.has.domain.Comment;
import homes.has.domain.Post;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Getter
public class CommentDto {
    private Long id;
    private String body;
    private int likes;
    private Long postId;
    private UUID memberId;
    private Comment parent;
    private List<Comment> children;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    @Builder
    public CommentDto(Long id, String body, int likes, Long postId, UUID memberId,Comment parent
            ,List<Comment> children ,LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id= id;
        this.body = body;
        this.likes = likes;
        this.postId= postId;
        this.memberId = memberId;
        this.parent=parent;
        this.children = children;
        this.createdAt= createdAt;
        this.modifiedAt=modifiedAt;
    }

}
