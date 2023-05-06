package homes.has.service;

import homes.has.domain.Comment;
import homes.has.domain.Post;
import homes.has.dto.CommentDto;
import homes.has.repository.CommentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


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

    public void decreaseLikes(Long commentId){
        commentRepository.decreaseLikes(commentId);
    }

    public void delete(Long commentId){
        commentRepository.deleteById(commentId);
    }

    public List<Comment> findByPostId(Long postId){
        return commentRepository.findByPostId(postId);
    }
}
