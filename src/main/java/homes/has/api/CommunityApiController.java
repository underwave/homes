package homes.has.api;


import homes.has.domain.Category;
import homes.has.domain.Comment;
import homes.has.domain.Member;
import homes.has.domain.Post;
import homes.has.dto.CommentDto;
import homes.has.dto.PostDto;
import homes.has.repository.PostSearchCond;
import homes.has.service.CommentService;
import homes.has.service.MemberService;
import homes.has.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


import java.util.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CommunityApiController {

    private final PostService postService;
    private final CommentService commentService;
    private final MemberService memberService;

//    커뮤니티 메인 카테고리별 최신글 3개
    @GetMapping("/community")
    public Map<Category, List<PostDto>> createCommunityMain(){

        Map<Category, List<PostDto>> categoryListMap = postService.communityMainPost();
        return categoryListMap;
    }


//    카테고리 내 검색
    @GetMapping("/community/{category}/search")
    public List<PostDto> searchPost(@PathVariable Category category, @RequestParam String word){
        List<PostDto> posts = postService.findByWord(new PostSearchCond(word, category));

        return posts;
    };

//     post 전체 검색
    @GetMapping("/community/search")
    public List<PostDto> searchPost(@RequestParam String word){
        List<PostDto> posts = postService.findByWord(new PostSearchCond(word));

        return posts;
    };

    @PostMapping("/community/{category}/write")
    public void writePost(@PathVariable Category category, PostDto postDto){
        Member member = memberService.findById(postDto.getMemberId()).get();
        Post build = Post.builder()
                .member(member)
                .title(postDto.getTitle())
                .body(postDto.getBody())
                .category(postDto.getCategory())
                .imageUrl(postDto.getImageUrl())
                .build();
        postService.save(build);
    }

// api 22
    @GetMapping("/community/{category}/{postId}")
    public PostDto postDetail(@PathVariable Category category, @PathVariable Long postId){

        Post post = postService.findById(postId).get();
        Map<PostDto,List<CommentDto>> map = new HashMap<>();
        List<Comment> comments = commentService.findByPostId(postId);
        List<CommentDto> commentDtos= new ArrayList<>();
        for (Comment comment : comments) {
            CommentDto build = CommentDto.builder()
                    .id(comment.getId())
                    .likes(comment.getLikes())
                    .body(comment.getBody())
                    .children(comment.getChildren())
                    .memberId(comment.getMember().getId())
                    .postId(comment.getPost().getId())
                    .createAt(comment.getCreatedAt())
                    .modifiedAt(comment.getModifiedAt())
                    .build();
            commentDtos.add(build);
        }

        PostDto postDto = PostDto.builder()
                .memberId(post.getMember().getId())
                .memberLoc(post.getMember().getLocation())
                .category(post.getCategory())
                .id(post.getId())
                .title(post.getTitle())
                .body(post.getBody())
                .likes(post.getLikes())
                .comments(post.getComments())
                .comment(commentDtos)
                .build();
        return postDto;
    }

//    명세 23번
    @DeleteMapping("/community/{category}/{postId}")
    public void deletePost(@PathVariable Long postId){
        postService.deletePost(postId);
    }


// 명세 27
    @PostMapping("/community/{category}/{postId}")
    public void writeComment(@PathVariable Long postId, CommentDto commentDto){
        Member member = memberService.findById(commentDto.getMemberId()).get();
        Post post = postService.findById(postId).get();
        Comment build = Comment.builder()
                .body(commentDto.getBody())
                .member(member)
                .post(post)
                .parent(commentDto.getParent())
                .build();
        commentService.save(build);
    }

}
