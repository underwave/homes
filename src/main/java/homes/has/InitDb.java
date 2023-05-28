package homes.has;

import homes.has.domain.*;
import homes.has.enums.Category;
import homes.has.enums.Valid;
import homes.has.service.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
public class InitDb {

    @Autowired
    private MemberService memberService;
    @Autowired
    private PostService postService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private ImageFileService imageFileService;

    @Autowired
    private LikePostService likePostService;
    @PostConstruct
    public void init() throws IOException {
        Member member1 = Member.builder().name("김철수")
                .valid(Valid.CERTIFIED)
                .location("가좌로 3길")
                .nickName("oneroom")
                .id("abc")
                .build();
        Member member2 = Member.builder().name("aaa")
                .valid(Valid.CERTIFIED)
                .location("가좌로 2길")
                .nickName("helloWorld")
                .id("def")

                .build();
        Member member3 = Member.builder().name("신지현")
                .valid(Valid.CERTIFIED)
                .location("가좌로 1길")
                .nickName("lorem")
                .id("ghi")

                .build();
        memberService.save(member1);
        memberService.save(member2);
        memberService.save(member3);

        createPost(member1, Category.GENERAL, "akima", "bkimb");
        createPost(member2, Category.GENERAL, "akima", "bkimb");
        Post post2 = createPost(member3, Category.GENERAL, "akima", "bkimb");
        Post post1 = createPost(member1, Category.GENERAL, "akima", "bkimb");

        createPost(member2, Category.MARKET, "aa", "aa");
        createPost(member3, Category.MARKET, "bb", "bb");
        createPost(member1, Category.MARKET, "cc", "cc");
        createPost(member2, Category.MARKET, "dd", "dd");

        createPost(member3, Category.TIPS, "akima", "bkimb");
        createPost(member1, Category.TIPS, "akima", "bkimb");
        createPost(member2, Category.TIPS, "akima", "bkimb");
        createPost(member3, Category.TIPS, "akima", "bkimb");

//      이미지 출력 테스트용
//        createPost(member3, Category.GENERAL, "안녕", "반가워");


        commentService.save(Comment.builder().member(member3).post(post1).body("ㄹㅇㅋㅋ").build());
        commentService.save(Comment.builder().member(member1).post(post1).body("맞아맞아").build());
        commentService.save(Comment.builder().member(member2).post(post1).body("나도 그렇게 생각해").build());

        likePostService.save(new LikePosts(post1,member1));
        likePostService.save(new LikePosts(post1,member2));
        likePostService.save(new LikePosts(post1,member3));

        for (int i = 0; i < 5; i++) {
            postService.increaseLikes(post1.getId());
        }
        for (int i = 0; i < 7; i++) {
            postService.increaseLikes(post2.getId());
        }

        String location = "진주대로 500번길";
        ReviewGrade grade = new ReviewGrade(5, 3, 2, 4);
        ReviewBody body = new ReviewBody("볕이 잘 들고... 뷰  가 조타...ㅎㅎ", "건물 옆에 커다란 느티나무가 있는데요.. 그래서인지 벌레가 너무 많이 꼬임...", "벽지가  넘무 파래여 집에 있으면 창백해보임니다");

        String location2 = "진주대로 550번길";
        ReviewGrade grade2 = new ReviewGrade(4, 4, 1, 4);
        ReviewBody body2 = new ReviewBody("주인장이 뿌링클을 사주심니다 ...냠냠..", "학교랑 넘나리 먹어요  ", "몰...루");

        ReviewGrade grade3 = new ReviewGrade(4, 4, 1, 4);
        ReviewBody body3 = new ReviewBody("테테테테테테테스트", "....", "테스트 임니다아 500번길이요오");

        // when
        reviewService.CreateReview(member1.getId(),location, grade, body,37.1,128.2, null);
        reviewService.CreateReview(member2.getId(),location2, grade2, body2,37.2,125.2, null);
        reviewService.CreateReview(member2.getId(),location2, grade3, body3,37.1,128.2, null);
        reviewService.CreateReview(member1.getId(),location, grade, body,37.1,128.2, null);

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
    private Post createPost(Member member , Category category, String body, String title, MultipartFile file) {
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
