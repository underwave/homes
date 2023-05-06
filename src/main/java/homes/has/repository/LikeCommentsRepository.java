package homes.has.repository;

import homes.has.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LikeCommentsRepository extends JpaRepository<LikeComments,Long> {


    public boolean existsByCommentAndMember(Comment comment, Member member);

    @Query("SELECT lc FROM LikePosts lc WHERE lc.comment.id = :commentId AND lc.member.id = :memberId")
    public LikeComments findByCommentIdAndMemberId(@Param("commentId") Long commentId, @Param("memberId") Long memberId);




}
