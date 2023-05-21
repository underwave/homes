package homes.has.api;


import homes.has.domain.LocRequest;
import homes.has.domain.Member;
import homes.has.domain.Valid;
import homes.has.dto.LocRequestForm;
import homes.has.service.DetectAdminService;
import homes.has.service.LocRequestService;
import homes.has.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AdminApiController {
    private final DetectAdminService detectAdminService;
    private final LocRequestService locRequestService;
    private final MemberService memberService;


    @GetMapping("/user/{memberId}/admin")
    public boolean isAdmin(@PathVariable Long memberId){
        return detectAdminService.isAdmin(memberId);
    }


    @GetMapping("/admin")
    public List<LocRequestForm> printRequests(){
        List<LocRequest> locRequests = locRequestService.findAll();
        List<LocRequestForm> locRequestForms = new ArrayList<>();
        for (LocRequest locRequest : locRequests) {
            LocRequestForm locRequestForm = LocRequestForm.builder()
                    .member(locRequest.getMember())
                    .location(locRequest.getLocation())
                    .imageUrl(locRequest.getImageUrl())
                    .build();
            locRequestForms.add(locRequestForm);
        }
        return locRequestForms;
    }

    @PostMapping("admin/LocRequest/{requestId}")
    public void acceptRequest(@PathVariable Long requestId){
        LocRequest locRequest = locRequestService.findById(requestId);
        String location = locRequest.getLocation();
        Member member = locRequest.getMember();
        memberService.changeLocation(member, location);
        locRequestService.delete(requestId);
    }

    @DeleteMapping("admin/LocRequest/{requestId}")
    public void rejectRequest(@PathVariable Long requestId){
        LocRequest locRequest = locRequestService.findById(requestId);
        Member member = locRequest.getMember();
        locRequestService.delete(requestId);
    }

}
