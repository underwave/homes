package homes.has.repository;

import homes.has.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface LikeCommentsRepository extends JpaRepository<LikeComments,Long> {


    public boolean existsByCommentAndMember(Comment comment, Member member);

    @Query("SELECT lc FROM LikeComments lc WHERE lc.comment.id = :commentId AND lc.member.id = :memberId")
    public LikeComments findByCommentIdAndMemberId(@Param("commentId") Long commentId, @Param("memberId") UUID memberId);

    public List<LikeComments> findByCommentId(Long commentId);



}
