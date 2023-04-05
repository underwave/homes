package homes.has.domain.timestamp;

import homes.has.domain.Category;
import homes.has.domain.Member;
import homes.has.domain.Post;
import homes.has.repository.MemberRepository;
import homes.has.service.MemberService;
import homes.has.service.PostService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.BootstrapWith;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;


@Slf4j
@SpringBootTest
@Transactional
@Rollback(false)
class BaseEntityTest {

    @Autowired
    MemberService memberService;
    @Autowired
    PostService postService;
    @Autowired
    EntityManager em;


    @Test
    void timestampTest() throws InterruptedException {
        Member member = new Member();
        memberService.save(member);
        Post post = new Post(member, "abc", "abcde", Category.GENERAL);

        Long postId = postService.save(post);

        Thread.sleep(1000);

        post.setBody("asdsa");
        Post findPost = postService.findById(postId).get();
        em.flush();
        em.clear();

        LocalDateTime createdAt = findPost.getCreatedAt();
        LocalDateTime modifiedAt = findPost.getModifiedAt();
        log.info("createdAt={}" ,createdAt);
        log.info("modifiedAt={}" ,modifiedAt);

        postService.increaseComments(postId);
        Thread.sleep(1000);

        Post findPost2 = postService.findById(postId).get();
        LocalDateTime createdAt2 = findPost2.getCreatedAt();
        LocalDateTime modifiedAt2 = findPost2.getModifiedAt();
        log.info("createdAt={}" ,createdAt);
        log.info("modifiedAt2={}" ,modifiedAt2);


        assertThat(createdAt).isEqualTo(createdAt2);
        assertThat(createdAt).isNotEqualTo(modifiedAt);
        assertThat(modifiedAt).isEqualTo(modifiedAt2);




    }
}