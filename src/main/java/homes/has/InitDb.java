package homes.has;

import homes.has.domain.*;
import homes.has.service.CommentService;
import homes.has.service.MemberService;
import homes.has.service.PostService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InitDb {

    @Autowired
    private MemberService memberService;
    @Autowired
    private PostService postService;
    @Autowired
    private CommentService commentService;

    @PostConstruct
    public void init(){
        Member member1 = new Member(Valid.CERTIFIED, "가좌로 3길");
        Member member2 = new Member(Valid.CERTIFIED, "가좌로 2길");
        Member member3 = new Member(Valid.CERTIFIED, "가좌로 1길");
        memberService.save(member1);
        memberService.save(member2);
        memberService.save(member3);

        createPost(member1, Category.GENERAL, "akima", "bkimb");
        createPost(member2, Category.GENERAL, "akima", "bkimb");
        createPost(member3, Category.GENERAL, "akima", "bkimb");
        Post post1 = createPost(member1, Category.GENERAL, "akima", "bkimb");

        createPost(member2, Category.MARKET, "aa", "aa");
        createPost(member3, Category.MARKET, "bb", "bb");
        createPost(member1, Category.MARKET, "cc", "cc");
        createPost(member2, Category.MARKET, "dd", "dd");

        createPost(member3, Category.TIPS, "akima", "bkimb");
        createPost(member1, Category.TIPS, "akima", "bkimb");
        createPost(member2, Category.TIPS, "akima", "bkimb");
        createPost(member3, Category.TIPS, "akima", "bkimb");

        commentService.save(Comment.builder().member(member3).post(post1).body("ㄹㅇㅋㅋ").build());
        commentService.save(Comment.builder().member(member1).post(post1).body("맞아맞아").build());
        commentService.save(Comment.builder().member(member2).post(post1).body("나도 그렇게 생각해").build());
    }

    private Post createPost(Member member , Category category, String body, String title) {
        Post post = Post.builder()
                .member(member)
                .title(title)
                .body(body)
                .category(category)
                .build();
        Long saved = postService.save(post);
        return post;
    }

}
