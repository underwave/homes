package homes.has.service;


import homes.has.domain.*;
import homes.has.repository.CommentRepository;
import homes.has.repository.MemberRepository;
import homes.has.repository.PostRepository;
import homes.has.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final ReviewRepository reviewRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    public Long save(Member member){
        return memberRepository.save(member).getId();
    }

    public Optional<Member> findById(Long memberId){
        return memberRepository.findById(memberId);
    }

    public List<Member> findAll(){
        return memberRepository.findAll();
    }


    public void changeValid(Long memberId,Valid valid){
        Member member = memberRepository.findById(memberId).get();
        member.changeValid(valid);
    }

    public void changeValid(Member member ,Valid valid){
        member.changeValid(valid);
    }

    public void changeLocation(Long memberId,String location){
        Member member = memberRepository.findById(memberId).get();
        member.changeLocation(location);
    }

    public void changeLocation(Member member ,String location){
        member.changeLocation(location);
    }



    public List<Post> memberPost(Long memberId){
        return postRepository.memberPost(memberId);
    }

    public List<Review> memberReview(Long memberId){
        List<Review> reviews = reviewRepository.findByMemberId(memberId);
        if (reviews.size()==0){
            throw new IllegalArgumentException("리뷰를 찾을 수 없음");
        }
        return reviews;
    }

    public List<Comment> memberComment(Long memberId){
        return commentRepository.memberComment(memberId);
    }

    public Boolean isReviewed(Long memberId){
        return reviewRepository.existsByMemberId(memberId);
    }

}
