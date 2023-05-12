package homes.has.api;

import homes.has.domain.Comment;
import homes.has.domain.Member;
import homes.has.domain.Post;
import homes.has.dto.CommentDto;
import homes.has.dto.MemberDto;
import homes.has.dto.PostDto;
import homes.has.service.CommentService;
import homes.has.service.MemberService;
import homes.has.service.PostService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
public class UserApiController {
    private final PostService postService;
    private final CommentService commentService;
    private final MemberService memberService;


    @GetMapping("/user/{userId}")
    public MemberDto userInfo(@PathVariable Long userId){
        Member member = memberService.findById(userId).get();

        return MemberDto.builder()
                .location(member.getLocation())
                .valid(member.getValid())
                .name(member.getName())
                .build();
    }

    /*
    * 내가 쓴글 api 14번
    * */
    @GetMapping("/user/{userId}/post")
    public List<PostDto> userPost(@PathVariable Long memberId){
        List<Post> posts = postService.memberPost(memberId);
        List<PostDto> postDtos = new ArrayList<>();

        for (Post post : posts) {
            PostDto postDto = PostDto.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .body(post.getBody())
                    .comments(post.getComments())
                    .memberLoc(post.getMember().getLocation())
                    .likes(post.getLikes())
                    .createdAt(post.getCreatedAt())
                    .modifiedAt(post.getModifiedAt())
                    .build();
            postDtos.add(postDto);
        }
        return postDtos;
    }

//    @GetMapping("/user/{userId}/comment")
//    public List<PostDto> userComment(@PathVariable Long memberId){
//        List<Comment> comments = commentService.memberComment(memberId);
//        List<CommentDto> commentDtos = new ArrayList<>();
//
//    }

}
