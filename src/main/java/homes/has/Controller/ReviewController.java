package homes.has.Controller;

import homes.has.domain.Building;
import homes.has.domain.Category;
import homes.has.dto.PostDto;
import homes.has.repository.BuildingRepository;
import homes.has.repository.ReviewRepository;
import homes.has.service.PostService;
import homes.has.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/map")
@RequiredArgsConstructor
public class ReviewController{
    private final ReviewService reviewService;


    /**
     * 빌딩 리스트 반환
     **/

    @GetMapping("/building")
    public List<Building> getBuildingList(@RequestParam double latitude, @RequestParam double longitude, @RequestParam double distance) {
        List<Building> buildingList = reviewService.GetBuildingsByLocation(latitude, longitude, distance);
        return buildingList;
    }





}
