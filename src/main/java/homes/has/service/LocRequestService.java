package homes.has.service;


import homes.has.domain.LocRequest;
import homes.has.repository.LocRequestRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class LocRequestService {
    private final LocRequestRepository locRequestRepository;


    public Long save(LocRequest locRequest){
        return locRequestRepository.save(locRequest).getId();
    };

    public void delete(Long id){
        locRequestRepository.deleteById(id);
    }

    public void findByMemberId(Long memberId){
        LocRequest LocRequest = locRequestRepository.findByMemberId(memberId);
    }

}
