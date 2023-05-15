package homes.has.api;

import homes.has.domain.*;
import homes.has.dto.LocRequestForm;
import homes.has.dto.MemberDto;
import homes.has.dto.PostDto;
import homes.has.service.CommentService;
import homes.has.service.LocRequestService;
import homes.has.service.MemberService;
import homes.has.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
public class UserApiController {
    private final PostService postService;
    private final CommentService commentService;
    private final MemberService memberService;
    private final LocRequestService locRequestService;

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

    @GetMapping("/user/{userId}/comment")
    public List<PostDto> userComment(@PathVariable Long memberId){
        List<Comment> comments = commentService.memberComment(memberId);
        List<Post> posts= new ArrayList<>();
        List<PostDto> postDtos = new ArrayList<>();
        for (Comment comment : comments) {
            Long postId = comment.getPost().getId();
            Post post = postService.findById(postId).get();
            posts.add(post);
        }

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


    @PostMapping("/user/authorization/write")
    public void writeLocRequest(@RequestBody LocRequestForm locRequestForm){
        Long memberId = locRequestForm.getMemberId();
        Member member = memberService.findById(memberId).get();

        LocRequest locRequest = LocRequest.builder()
                .location(locRequestForm.getLocation())
                .imageUrl(locRequestForm.getImageUrl())
                .member(member)
                .build();
        locRequestService.save(locRequest);
        memberService.changeValid(memberId, Valid.ONGOING);
    }

    @DeleteMapping("/user/authorization/{locRequestId}")
    public void deleteLocRequest(@PathVariable Long locRequestId, @RequestBody Long memberId){
        Member member = memberService.findById(memberId).get();
        locRequestService.delete(locRequestId);
        member.changeValid(Valid.UNCERTIFIED);
    }



}
