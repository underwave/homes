package homes.has.dto;


import homes.has.domain.Category;
import homes.has.domain.Member;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class PostDto {

    private Long id;

    private String title;

    private String body;

    private Category category;

    private String imageUrl;

    private int likes;

    private int comments;
    private Long memberId;
    private String memberLoc;
    private List<CommentDto> comment;

    @Builder
    public PostDto(Long memberId, String memberLoc, Category category, Long id , String imageUrl,String title, String body, int likes, int comments, List<CommentDto> comment) {
        this.memberId = memberId;
        this.memberLoc = memberLoc;
        this.body = body;
        this.imageUrl=imageUrl;
        this.category=category;
        this.id =id;
        this.title = title;
        this.likes = likes;
        this.comments = comments;
        this.comment = comment;
    }



}
