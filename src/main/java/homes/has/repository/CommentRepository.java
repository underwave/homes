package homes.has.repository;

import homes.has.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment,Long> {


    @Modifying(clearAutomatically = true)
    @Query("update Comment c set c.likes = c.likes+1 where c.id = :id")
    public void increaseLikes(@Param("id")Long id);

}
