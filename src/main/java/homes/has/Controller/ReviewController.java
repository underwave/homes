package homes.has.Controller;

import homes.has.domain.Building;
import homes.has.domain.Category;
import homes.has.domain.Review;
import homes.has.dto.BuildingsDto;
import homes.has.dto.CreateReviewDto;
import homes.has.dto.PostDto;
import homes.has.repository.BuildingRepository;
import homes.has.repository.ReviewRepository;
import homes.has.service.PostService;
import homes.has.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/map")
@RequiredArgsConstructor
public class ReviewController{
    private final ReviewService reviewService;


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
    public List<Review> getReviewList(@PathVariable String location) {
        List<Review> reviewList = reviewService.GetReviewList(location);
        return reviewList;
    }

    /**
     * 리뷰 작성 (API no.4)
     **/
    @PostMapping("{location}/review/write")
    public void createReview(@RequestBody CreateReviewDto request) throws IOException {
        List<MultipartFile> imageFiles = request.getImageFiles();
        reviewService.CreateReview(request.getMember(), request.getLocation(), request.getGrade(),
                request.getBody(), request.getPosx(), request.getPosy(), imageFiles);
    }
}
