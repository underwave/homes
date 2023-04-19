package homes.has;

import homes.has.domain.Category;
import homes.has.domain.Member;
import homes.has.domain.Post;
import homes.has.service.MemberService;
import homes.has.service.PostService;
import jakarta.annotation.PostConstruct;
import org.hibernate.annotations.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InitDb {

    @Autowired
    private MemberService memberService;
    @Autowired
    private PostService postService;

    @PostConstruct
    public void init(){
        Member member = new Member();
        memberService.save(member);
        Post post1 = createPost(member, Category.GENERAL, "akima", "bkimb");
        Post post2 = createPost(member, Category.GENERAL, "akima", "bkimb");
        Post post3 = createPost(member, Category.GENERAL, "akima", "bkimb");
        Post post4 = createPost(member, Category.GENERAL, "akima", "bkimb");

        Post post5 = createPost(member, Category.MARKET, "aa", "aa");
        Post post6 = createPost(member, Category.MARKET, "bb", "bb");
        Post post7 = createPost(member, Category.MARKET, "cc", "cc");
        Post post8 = createPost(member, Category.MARKET, "dd", "dd");

        Post post9 = createPost(member, Category.TIPS, "akima", "bkimb");
        Post post10 = createPost(member, Category.TIPS, "akima", "bkimb");
        Post post11 = createPost(member, Category.TIPS, "akima", "bkimb");
        Post post12 = createPost(member, Category.TIPS, "akima", "bkimb");
    }

    private Post createPost(Member member , Category category, String body, String title) {
        Post post = new Post(member, title,body,category);
        Long saved = postService.save(post);
        return post;
    }

}
