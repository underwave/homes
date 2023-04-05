package homes.has.service;

import homes.has.domain.Category;
import homes.has.domain.Member;
import homes.has.domain.Post;
import homes.has.repository.PostQueryRepository;
import homes.has.repository.PostRepository;
import homes.has.repository.PostSearchCond;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PostServiceTest {

    @Autowired
    PostRepository postRepository;
    @Autowired
    PostQueryRepository queryRepository;
    @Autowired
    PostService postService;




    @Test
    void 게시글_검색() {
        Member member = new Member();

        Post post1 = createPost(member, Category.GENERAL, "akima", "bkimb");
        Post post2 = createPost(member,null, "akima", "aaaa");
        Post post3 = createPost(member, Category.GENERAL, "aaaa", "aaaa");

        PostSearchCond kim = new PostSearchCond("kim",Category.GENERAL);
        PostSearchCond kim2 = new PostSearchCond("kim");

        List<Post> findPost = postService.findByWord(kim);
        List<Post> findPost2 = postService.findByWord(kim2);

        assertThat(findPost).contains(post1);
        assertThat(findPost).doesNotContain(post2,post3);

        assertThat(findPost2).contains(post1,post2);
        assertThat(findPost2).doesNotContain(post3);
    }

    private Post createPost(Member member , Category category, String body, String title) {
        Post post = new Post(member, title,body,category);
        Long saved = postService.save(post);
        return post;
    }


}