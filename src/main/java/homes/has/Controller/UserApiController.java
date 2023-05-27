package homes.has.Controller;

import homes.has.domain.*;
import homes.has.dto.LocRequestForm;
import homes.has.dto.MemberDto;
import homes.has.dto.PostDto;
import homes.has.dto.ReviewDto;
import homes.has.enums.FilePath;
import homes.has.enums.Valid;
import homes.has.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    private final ReviewService reviewService;
    private final ImageFileService imageFileService;


    @PostMapping("/user/sign")
    public void signUp(@RequestBody MemberDto memberDto) throws Exception {
        String id = memberDto.getId();
        if(!memberService.isExist(id)) {
            throw new Exception("이미 존재하는 id");
        }
        Member.builder()
            .valid(Valid.UNCERTIFIED)
            .id(id)
            .name(memberDto.getName())
            .nickName(memberDto.getNickName())
            .build();
    }
    
    
    
    @GetMapping("/user/{memberId}")
    public MemberDto userInfo(@PathVariable String userId){
        Member member = memberService.findById(userId).get();

        return MemberDto.builder()
                .location(member.getLocation())
                .name(member.getName())
                .nickName(member.getNickName())
                .build();
    }

    /*
    * 내가 쓴글 api 14번
    * */
    @GetMapping("/user/{memberId}/post")
    public List<PostDto> userPost(@PathVariable String memberId){
        List<Post> posts = memberService.memberPost(memberId);
        List<PostDto> postDtos = new ArrayList<>();

        for (Post post : posts) {
            PostDto postDto = createPostDto(memberId, post);
            postDtos.add(postDto);
        }
        return postDtos;
    }

    /*
     *  api 명세 15, 내가 쓴 댓글
    * */
    @GetMapping("/user/{memberId}/comment")
    public List<PostDto> userComment(@PathVariable String memberId){
        List<Comment> comments = memberService.memberComment(memberId);
        List<Post> posts= new ArrayList<>();
        List<PostDto> postDtos = new ArrayList<>();
        for (Comment comment : comments) {
            Long postId = comment.getPost().getId();
            Post post = postService.findById(postId).get();
            posts.add(post);
        }

        for (Post post : posts) {
            PostDto postDto = createPostDto(memberId, post);
            postDtos.add(postDto);
        }
        return postDtos;
    }


    /*
    *  api 명세 11, 주소지 인증
    * */
    @PostMapping("/user/authorization/write")
    public void writeLocRequest(@RequestBody LocRequestForm locRequestForm) throws IOException {
        String memberId = locRequestForm.getMemberId();
        Member member = memberService.findById(memberId).get();
        ImageFile imageFile = imageFileService.saveFile(locRequestForm.getFile(), FilePath.LOCREQUEST);

        LocRequest locRequest = LocRequest.builder()
                .location(locRequestForm.getLocation())
                .member(member)
                .imageFile(imageFile)
                .build();

        locRequestService.save(locRequest);
        if(member.getValid()== Valid.UNCERTIFIED)
            member.changeValid(Valid.ONGOING);
    }

    /*
    *api 명세 12, 주소지 인증 취소
    * */
    @DeleteMapping("/user/authorization/{locRequestId}")
    public void deleteLocRequest(@PathVariable Long locRequestId, @RequestBody String memberId){
        Member member = memberService.findById(memberId).get();
        LocRequest locRequest = locRequestService.findById(locRequestId);
        ImageFile imageFile = locRequest.getImageFile();
        locRequestService.delete(locRequest);
        imageFileService.delete(imageFile);
        if(member.getValid()==Valid.ONGOING)
            member.changeValid(Valid.UNCERTIFIED);
    }
    /*
    * api 명세 13, 내가쓴 리뷰 출력 수정 필요
    * */
    @GetMapping("/user/{memberId}/review")
    public List<ReviewDto> userReview(@PathVariable String memberId){
        List<Review> reviews = memberService.memberReview(memberId);
        List<ReviewDto> reviewDtos = new ArrayList<>();

        for (Review review : reviews) {
            ReviewDto reviewDto = ReviewDto.builder()
                .grade(review.getGrade())
                .memberId(review.getMember().getId())
                .body(review.getBody())
                .building(review.getBuilding())
                .createdAt(review.getCreatedAt())
                .modifiedAt(review.getModifiedAt())
                .id(review.getId())
                .build();
            reviewDtos.add(reviewDto);
        }
        return reviewDtos;
    }

    private static PostDto createPostDto(String memberId, Post post) {
        Member member = post.getMember();
        String authorName = member.getLocation() + "_"+ member.getNickName().charAt(0);
        PostDto postDto = PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .body(post.getBody())
                .commentCount(post.getCommentCount())
                .authorName(authorName)
                .memberId(memberId)
                .likes(post.getLikes())
                .createdAt(post.getCreatedAt())
                .modifiedAt(post.getModifiedAt())
                .build();
        return postDto;
    }


}
