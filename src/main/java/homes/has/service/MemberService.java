package homes.has.service;


import homes.has.domain.Member;
import homes.has.repository.MemberRepository;
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


    public Long save(Member member){
        return memberRepository.save(member).getId();
    }

    public Optional<Member> findById(Long memberId){
        return memberRepository.findById(memberId);
    }

    public List<Member> findAll(){
        return memberRepository.findAll();
    }


}
