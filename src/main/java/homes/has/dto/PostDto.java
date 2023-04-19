package homes.has.dto;


import homes.has.domain.Category;
import homes.has.domain.Member;
import lombok.Data;

@Data
public class PostDto {

    private Long id;

    private String title;

    private String body;

    private Category category;

    private String imageUrl;

    private int likes;

    private int comments;

    private Member member;

    public PostDto(){};
    public PostDto(Category category, Long id , String title, int likes, int comments) {
        this.category=category;
        this.id =id;
        this. title = title;
        this.likes = likes;
        this.comments = comments;
    }

    public PostDto(Member member, Category category, Long id , String title, String body, int likes, int comments) {
        this.member = member;
        this.body = body;
        this.category=category;
        this.id =id;
        this. title = title;
        this.likes = likes;
        this.comments = comments;
    }



}
