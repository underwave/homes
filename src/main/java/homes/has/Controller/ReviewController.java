package homes.has.Controller;

import homes.has.domain.*;
import homes.has.dto.BuildingsDto;
import homes.has.dto.CreateReviewDto;
import homes.has.enums.Valid;
import homes.has.service.MemberService;
import homes.has.service.ReviewService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/map")
@RequiredArgsConstructor
public class ReviewController{
    private final ReviewService reviewService;
    private final MemberService memberService;


    /*
     * 빌딩 리스트 반환 (API no.1,2)

    @GetMapping("/building")
    public List<Building> getBuildingList(@RequestParam double latitude, @RequestParam double longitude, @RequestParam double distance) {
        List<Building> buildingList = reviewService.GetBuildingsByLocation(latitude, longitude, distance);
        return buildingList;
    } */

    /**
     * 빌딩 리스트 반환 (API no.1,2)
     **/
    @GetMapping("/building")
    public List<BuildingsDto> getBuildingList(@RequestParam double latitude, @RequestParam double longitude, @RequestParam double distance, @RequestParam Long memberid) {
        List<BuildingsDto> buildingList = reviewService.GetBuildingsForMap(latitude, longitude, distance, memberid);
        return buildingList;
    }

    /**
     * 특정 건물 리뷰 리스트 반환 (API no.3)
     **/
    @GetMapping("/{location}/detail")
    public List<Review> getReviewList(@PathVariable("location") String location) {
        List<Review> reviewList = reviewService.GetReviewList(location);
        return reviewList;
    }

    /**
     * 리뷰 작성 (API no.4)
     **/
    @PostMapping("/{location}/review/write")
    public void createReview(@RequestBody CreateReviewDto createReviewDto) throws IOException {
        reviewService.CreateReview(createReviewDto.getMemberId(),createReviewDto.getLocation(), createReviewDto.getGrade(),
                createReviewDto.getBody(), createReviewDto.getPosx(), createReviewDto.getPosy());
    }

    /**
     * 리뷰 수정 폼 생성 (API no.5)
     **/
    @GetMapping("/{location}/{reviewId}/modify")
    public Review getReviewById(@PathVariable("location") String location, @PathVariable("reviewId") Long id) {
        return reviewService.getReviewById(id);
    }

    /**
     * 리뷰 수정 (API no.6)
     **/
    @PutMapping("/{location}/{reviewId}/modify2")
    public void updateReview(@PathVariable("location") String location, @PathVariable("reviewId") Long id, @RequestBody UpdateReviewRequest request) {
        ReviewBody body = request.getBody();
        ReviewGrade grade = request.getGrade();
        reviewService.UpdateReview(id, grade, body);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class UpdateReviewRequest {
        private ReviewBody body;
        private ReviewGrade grade;
    }

    /**
     * 리뷰 삭제 (API no.7)
     **/
    @DeleteMapping("/{location}/{reviewId}")
    public void deleteReview(@PathVariable("location") String location, @PathVariable("reviewId") Long id) {
        /*
       * review 삭제시 db에 멤버의 review가 없으면 uncertified로 갱신
       * */
       Review review = reviewService.getReviewById(id);
       Member member = review.getMember();

       reviewService.DeleteReview(id);

       if(!memberService.isReviewed(member.getId()))
           memberService.changeValid(member, Valid.UNCERTIFIED);
    }
}
