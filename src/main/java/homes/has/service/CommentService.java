package homes.has.service;

import homes.has.domain.Comment;
import homes.has.domain.Post;
import homes.has.repository.CommentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;


    public Long save(Comment comment) {
        return commentRepository.save(comment).getId();
    }


    public void increaseLikes(Long commentId){
        commentRepository.increaseLikes(commentId);
    }


}
