package homes.has.service;


import homes.has.domain.*;
import homes.has.repository.LikeCommentsRepository;
import homes.has.repository.LikePostsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeCommentsService {
    private final LikeCommentsRepository likeCommentsRepository;


    public Long save(LikeComments likeComments) {
        return likeCommentsRepository.save(likeComments).getId();

    }

    public void delete(Long likeCommentsId){
        likeCommentsRepository.deleteById(likeCommentsId);
    }

    public LikeComments findByCommentIdAndMemberId(Long commentId, Long memberId){
        return likeCommentsRepository.findByCommentIdAndMemberId(commentId,memberId);
    }

    public boolean isCommentLikedByMember(Comment comment, Member member) {
        return likeCommentsRepository.existsByCommentAndMember(comment, member);
    }
}
