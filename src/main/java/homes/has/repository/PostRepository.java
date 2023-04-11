package homes.has.repository;

import homes.has.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long>{


//    auditing 반영 안하기 위해서 사용
    @Modifying(clearAutomatically = true)
    @Query("update Post p set p.likes = p.likes+1 where p.id = :id")
    public void increaseLikes(@Param("id")Long id);

    @Modifying(clearAutomatically = true)
    @Query("update Post p set p.comments = p.comments+1 where p.id = :id")
    public void increaseComments(@Param("id")Long id);
}
