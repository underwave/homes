package homes.has.service;


import homes.has.domain.Category;
import homes.has.domain.Post;
import homes.has.dto.PostDto;
import homes.has.repository.PostQueryRepository;
import homes.has.repository.PostRepository;
import homes.has.repository.PostSearchCond;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostQueryRepository queryRepository;


    public Long save(Post post) {
        return postRepository.save(post).getId();
    }

    public Optional<Post>  findById(Long postId){
        return postRepository.findById(postId);
    }

//    게시판 검색 기능
    public List<PostDto> findByWord(PostSearchCond cond){
        return queryRepository.findPostByTitleAndBodyInCategoryContaining(cond);
    }

    public void increaseLikes(Long postId){
        postRepository.increaseLikes(postId);
    }

    public void increaseComments(Long postId){
        postRepository.increaseComments(postId);
    }

//    main에 노출할 최신글 3개, map으로 return
    public Map<Category,List<PostDto>> communityMainPost(){
        Map<Category,List<PostDto>> map = new HashMap<>();

        for (Category cat : Category.values()) {
            List<Post> catPost = postRepository.findTop3ByCategoryOrderByCreatedAtDesc(cat);
            List<PostDto> postDtos= new ArrayList<>();
            for (Post post : catPost) {
                PostDto postDto = PostDto.builder()
                        .id(post.getId())
                        .category(post.getCategory())
                        .title(post.getTitle())
                        .body(post.getBody())
                        .likes(post.getLikes())
                        .comments(post.getComments())
                        .build();
                postDtos.add(postDto);
            }
            map.put(cat,postDtos);
        }

        return map;
    }
}
