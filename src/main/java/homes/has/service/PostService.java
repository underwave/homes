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
    public List<Post> findByWord(PostSearchCond cond){
        return queryRepository.findPostByTitleAndBodyInCategoryContaining(cond);
    }

//    public Long update(Long postId, String title, String body){
//        Post findPost = findById(postId).get();
//        if(title!= null)
//            findPost.setTitle(title);
//        if(body!= null)
//            findPost.setBody(body);
//        return findPost.getId();
//    }

    public void increaseLikes(Long postId){
        postRepository.increaseLikes(postId);
    }

    public void increaseComments(Long postId){
        postRepository.increaseComments(postId);
    }

    public Map<Category,List<PostDto>> communityMainPost(){
        Map<Category,List<PostDto>> map = new HashMap<>();

        for (Category cat : Category.values()) {
            List<Post> catPost = postRepository.findTop3ByCategoryOrderByCreatedAtDesc(cat);
            List<PostDto> postDtos= new ArrayList<>();
            for (Post post : catPost) {
                PostDto postDto = new PostDto(post.getCategory(), post.getId(), post.getTitle(), post.getLikes(), post.getComments());
                postDtos.add(postDto);
            }
            map.put(cat,postDtos);
        }

        return map;
    }
}
