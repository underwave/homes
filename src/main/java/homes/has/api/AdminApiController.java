package homes.has.api;


import homes.has.domain.ImageFile;
import homes.has.domain.LocRequest;
import homes.has.domain.Member;
import homes.has.dto.LocRequestDto;
import homes.has.dto.LocRequestForm;
import homes.has.service.DetectAdminService;
import homes.has.service.ImageFileService;
import homes.has.service.LocRequestService;
import homes.has.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AdminApiController {
    private final DetectAdminService detectAdminService;
    private final LocRequestService locRequestService;
    private final MemberService memberService;
    private final ImageFileService imageFileService;


    @GetMapping("/user/{memberId}/admin")
    public boolean isAdmin(@PathVariable Long memberId){
        return detectAdminService.isAdmin(memberId);
    }


    @GetMapping("/admin")
    public List<LocRequestDto> printRequests(){
        List<LocRequest> locRequests = locRequestService.findAll();
        List<LocRequestDto> locRequestDtos = new ArrayList<>();
        for (LocRequest locRequest : locRequests) {
            LocRequestDto locRequestDto = LocRequestDto.builder()
                    .id(locRequest.getId())
                    .createdAt(locRequest.getCreatedAt())
                    .build();
            locRequestDtos.add(locRequestDto);
        }
        return locRequestDtos;
    }

    @GetMapping("admin/LocRequest/{requestId}")
    public LocRequestDto printRequest(@PathVariable Long requestId){
        LocRequest locRequest = locRequestService.findById(requestId);
        ResponseEntity<byte[]> image = imageFileService.printFile(locRequest.getImageFile());
        Member member = locRequest.getMember();
        return LocRequestDto.builder()
                .id(requestId)
                .nickname(member.getNickName())
                .name(member.getName())
                .location(locRequest.getLocation())
                .createdAt(locRequest.getCreatedAt())
                .image(image)
                .build();

    }




    @PostMapping("admin/LocRequest/{requestId}")
    public void acceptRequest(@PathVariable Long requestId){
        LocRequest locRequest = locRequestService.findById(requestId);
        String location = locRequest.getLocation();
        Member member = locRequest.getMember();
        memberService.changeLocation(member, location);
        ImageFile imageFile = locRequest.getImageFile();
        imageFileService.delete(imageFile);
        locRequestService.delete(locRequest);
    }

    @DeleteMapping("admin/LocRequest/{requestId}")
    public void rejectRequest(@PathVariable Long requestId){
        LocRequest locRequest = locRequestService.findById(requestId);
        Member member = locRequest.getMember();
        ImageFile imageFile = locRequest.getImageFile();
        imageFileService.delete(imageFile);
        locRequestService.delete(locRequest);
    }

}
