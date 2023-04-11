package homes.has.service;


import homes.has.domain.Post;
import homes.has.repository.PostQueryRepository;
import homes.has.repository.PostRepository;
import homes.has.repository.PostSearchCond;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

}
