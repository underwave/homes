package homes.has.Controller;


import homes.has.domain.*;
import homes.has.dto.CommentDto;
import homes.has.dto.PostDto;
import homes.has.enums.Category;
import homes.has.enums.FilePath;
import homes.has.repository.PostSearchCond;
import homes.has.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CommunityApiController {

    private final PostService postService;
    private final CommentService commentService;
    private final MemberService memberService;
    private final LikePostService likePostService;
    private final LikeCommentsService likeCommentsService;
    private final ImageFileService imageFileService;
    private final PostImageFileService postImageFileService;

//    커뮤니티 메인 카테고리별 최신글 3개
    @GetMapping("/community")
    public Map<Category, List<PostDto>> createCommunityMain(){

        Map<Category, List<PostDto>> categoryListMap = postService.communityMainPost();
        return categoryListMap;
    }

//    @GetMapping("/community/{category}/test")
//    public PostDto popularPost(@PathVariable Category category){
//        LocalDateTime start = LocalDateTime.now().minusDays(1);
//        LocalDateTime end = LocalDateTime.now();
//        Post post = postService.popularPost(category, start, end);
//
//        return createPostDto(post);
//    }


    @GetMapping("/community/{category}")
    public Map<String,Object> categoryPosts(@PathVariable Category category){
        Map<String,Object> map = new HashMap<>();
        List<Post> posts = postService.findByCategory(category);
        List<PostDto> normal = new ArrayList<>();
        for (Post post : posts) {
            PostDto postDto = createPostDto(post);
            normal.add(postDto);
        }

        LocalDateTime start = LocalDateTime.now().minusDays(1);
        LocalDateTime end = LocalDateTime.now();
        PostDto popularPostDto = createPostDto(postService.popularPost(category, start, end));

        normal.sort(Comparator.comparing(PostDto::getCreatedAt).reversed());
        map.put("popular",popularPostDto);
        map.put("normal", normal);
        return map;
    }



    //    카테고리 내 검색
    @GetMapping("/community/{category}/search")
    public List<PostDto> searchPost(@PathVariable Category category, @RequestParam String word){
        List<PostDto> posts = postService.findByWord(new PostSearchCond(word, category));
        posts.sort(Comparator.comparing(PostDto::getCreatedAt).reversed());

        return posts;
    };

//     post 전체 검색
    @GetMapping("/community/search")
    public List<PostDto> searchPost(@RequestParam String word){
        List<PostDto> posts = postService.findByWord(new PostSearchCond(word));
        posts.sort(Comparator.comparing(PostDto::getCreatedAt).reversed());
        return posts;
    };
     /*
     * api 22
     * 게시글 작성
     * */
    @PostMapping("/community/{category}/write")
    public void writePost(@PathVariable Category category,
                          @RequestPart PostDto postDto,
                          @RequestPart(required = false) List<MultipartFile> files) throws IOException {
        Member member = memberService.findById(postDto.getMemberId()).get();

        Post post = Post.builder()
                .member(member)
                .title(postDto.getTitle())
                .body(postDto.getBody())
                .category(category)
                .postImageFiles(new ArrayList<>())
                .build();
        if(files!=null) {
            for (MultipartFile multipartFile : files) {
                ImageFile imageFile = imageFileService.saveFile(multipartFile, FilePath.POST);
                PostImageFile postImageFile = postImageFileService.save(new PostImageFile(post, imageFile));
                post.getPostImageFiles().add(postImageFile);
            }
        }

        postService.save(post);
    }

    /*
    * api 23
    * 게시글 상세란
    * */
    @GetMapping("/community/{category}/{postId}")
    public PostDto postDetail(@PathVariable Category category, @PathVariable Long postId){

        Post post = postService.findById(postId).get();
        Map<PostDto,List<CommentDto>> map = new HashMap<>();
        List<Comment> comments = commentService.findByPostId(postId);
        List<CommentDto> commentDtos= new ArrayList<>();
        for (Comment comment : comments) {
            CommentDto build = createCommentDto(comment);
            commentDtos.add(build);
        }
        commentDtos.sort(Comparator.comparing(CommentDto::getCreatedAt).reversed());

        PostDto postDto = createPostDto(post, commentDtos);
        return postDto;
    }


    /*
    * 명세 23번
    * 게시글 삭제
    * */
    @DeleteMapping("/community/{category}/{postId}")
    public void deletePost(@PathVariable Long postId){
        Post post = postService.findById(postId).get();

        //이미지 파일 삭제
        for (PostImageFile postImageFile : post.getPostImageFiles()) {
            imageFileService.delete(postImageFile.getImageFile());
            postImageFileService.delete(postImageFile);
        }
        //댓글 삭제
        for (Comment comment : post.getComments()) {
            List<LikeComments> likeComments = likeCommentsService.findByCommentId(comment.getId());
            //likeComment 삭제
            for (LikeComments likeComment : likeComments) {
                likeCommentsService.delete(likeComment);
            }
            commentService.delete(comment);

        }
        //게시글 삭제
        postService.deletePost(postId);

        //likePost 삭제
        List<LikePosts> likePosts = likePostService.findByPostId(postId);
        for (LikePosts likePost : likePosts) {
            likePostService.delete(likePost);
        }


    }


// 명세 27
    @PostMapping("/community/{category}/{postId}/writeComment")
    public void writeComment(@PathVariable Long postId, @RequestBody CommentDto commentDto){
        Member member = memberService.findById(commentDto.getMemberId()).get();
        Post post = postService.findById(postId).get();
        Comment build = Comment.builder()
                .body(commentDto.getBody())
                .member(member)
                .post(post)
                .parent(commentDto.getParent())
                .build();
        commentService.save(build);
        postService.increaseComments(postId);
    }
//명세 26
    @PostMapping("/community/{category}/{postId}/like")
    public void likePost(@PathVariable Long postId, @RequestParam String memberId){
        Post post = postService.findById(postId).get();
        Member member = memberService.findById(memberId).get();
        if (likePostService.isPostLikedByMember(post,member)){
            LikePosts likePost = likePostService.findByPostIdAndMemberId(postId, memberId);
            likePostService.delete(likePost.getId());
            postService.decreaseLikes(postId);
        }
        else {
            likePostService.save(new LikePosts(post, member));
            postService.increaseLikes(postId);
        }
    }

//    comment 좋아요 기능
    @PostMapping("/community/{category}/{postId}/commentLike")
    public void likeComment( @RequestBody String memberId, @RequestBody Long commentId){
        Comment comment = commentService.findById(commentId).get();
        Member member = memberService.findById(memberId).get();
        if (likeCommentsService.isCommentLikedByMember(comment,member)){
            LikeComments likeComments = likeCommentsService.findByCommentIdAndMemberId(commentId, memberId);
            likeCommentsService.delete(likeComments.getId());
            commentService.decreaseLikes(commentId);
        }
        else {
            likeCommentsService.save(new LikeComments(comment, member));
            commentService.increaseLikes(commentId);
        }
    }



    // 명세 31
    @DeleteMapping("/community/{category}/{postId}/deleteComment")
    public void deleteComment(@PathVariable Long postId, @RequestBody Long commentId){
        commentService.delete(commentId);
        postService.decreaseComments(postId);
        //likeComment 삭제
        for (LikeComments likeComments : likeCommentsService.findByCommentId(commentId)) {
            likeCommentsService.delete(likeComments);
        }

    }


    @GetMapping("/community/{category}/{postId}/modify")
    public PostDto editPostForm(@PathVariable Long postId){
        Post post = postService.findById(postId).get();

        List<ResponseEntity<byte[]>> images = new ArrayList<>();
        for (PostImageFile postImageFile : post.getPostImageFiles()) {
            ImageFile imageFile = postImageFile.getImageFile();
            images.add(imageFileService.printFile(imageFile));
        }

        PostDto postDto = PostDto.builder()
                .category(post.getCategory())
                .id(post.getId())
                .title(post.getTitle())
                .body(post.getBody())
                .images(images)
                .build();
        return postDto;
    }
    @PostMapping("/community/{category}/{postId}/modify")
    public void editPost(@PathVariable Long postId, PostDto postDto) throws IOException {

        Post post = postService.findById(postId).get();
//      기존의 이미지 파일 삭제
        for (PostImageFile postImageFile : post.getPostImageFiles()) {
            imageFileService.delete(postImageFile.getImageFile());
            postImageFileService.delete(postImageFile);
        }

        List<MultipartFile> files = postDto.getFiles();
//        dto에서 받은 새로운 파일 등록
        for (MultipartFile multipartFile : files) {
            ImageFile imageFile = imageFileService.saveFile(multipartFile, FilePath.POST);
            PostImageFile postImageFile = postImageFileService.save(new PostImageFile(post, imageFile));
            post.getPostImageFiles().add(postImageFile);
        }

        postService.update(postId, postDto);
    }


    @GetMapping("/community/{category}/{postId}/{commentId}/commentModify")
    public CommentDto editCommentForm(@PathVariable Long commentId){
        Comment comment = commentService.findById(commentId).get();
        CommentDto commentDto = CommentDto.builder()
                .id(commentId)
                .body(comment.getBody())
                .build();
        return commentDto;
    }
    @PostMapping("/community/{category}/{postId}/{commentId}/commentModify")
    public void editComment(@PathVariable Long commentId, String body){
        commentService.update(commentId, body);
    }

    private static PostDto createPostDto(Post post) {
        Member member = post.getMember();
        String authorName = member.getLocation() + "_"+ member.getNickName().charAt(0);

        PostDto postDto = PostDto.builder()
                .id(post.getId())
                .category(post.getCategory())
                .commentCount(post.getCommentCount())
                .createdAt(post.getCreatedAt())
                .likes(post.getLikes())
                .title(post.getTitle())
                .body(post.getBody())
                .modifiedAt(post.getModifiedAt())
                .authorName(authorName)
                .memberId(member.getId())
                .build();
        return postDto;
    }

    private PostDto createPostDto(Post post, List<CommentDto> commentDtos) {
        Member member = post.getMember();
        String authorName = member.getLocation() + "_"+ member.getNickName().charAt(0);
        List<ResponseEntity<byte[]>> images = new ArrayList<>();
        for (PostImageFile postImageFile : post.getPostImageFiles()) {
            ImageFile imageFile = postImageFile.getImageFile();
            images.add(imageFileService.printFile(imageFile));
        }

        PostDto postDto = PostDto.builder()
                .authorName(authorName)
                .memberId(member.getId())
                .category(post.getCategory())
                .title(post.getTitle())
                .body(post.getBody())
                .images(images)
                .comments(commentDtos)
                .commentCount(post.getCommentCount())
                .likes(post.getLikes())
                .createdAt(post.getCreatedAt())
                .modifiedAt(post.getModifiedAt())
                .id(post.getId())
                .build();
        return postDto;
    }

    private static CommentDto createCommentDto(Comment comment) {
        CommentDto commentDto = CommentDto.builder()
                .id(comment.getId())
                .likes(comment.getLikes())
                .body(comment.getBody())
                .memberId(comment.getMember().getId())
                .postId(comment.getPost().getId())
                .createdAt(comment.getCreatedAt())
                .modifiedAt(comment.getModifiedAt())
                .children(comment.getChildren())
                .parent(comment.getParent())
                .build();
        return commentDto;
    }

}
