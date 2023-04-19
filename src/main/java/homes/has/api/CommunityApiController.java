package homes.has.api;


import homes.has.domain.Category;
import homes.has.dto.PostDto;
import homes.has.repository.PostSearchCond;
import homes.has.service.CommentService;
import homes.has.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CommunityApiController {

    private final PostService postService;
    private final CommentService commentService;

//    커뮤니티 메인 카테고리별 최신글 3개
    @GetMapping("/community")
    public Map<Category, List<PostDto>> createCommunityMain(){

        Map<Category, List<PostDto>> categoryListMap = postService.communityMainPost();
        log.info("categoryListMap= {} ", categoryListMap);
        return categoryListMap;
    }


//    카테고리 내 검색
    @GetMapping("/community/{category}/search?word={word}")
    public List<PostDto> searchPost(@PathVariable Category category, @PathVariable String word){
        List<PostDto> posts = postService.findByWord(new PostSearchCond(word, category));

        return posts;
    };

//     post 전체 검색
    @GetMapping("/community/search")
    public List<PostDto> searchPost(@RequestParam String word){
        List<PostDto> posts = postService.findByWord(new PostSearchCond(word));

        return posts;
    };


}
