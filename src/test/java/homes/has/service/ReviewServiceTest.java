package homes.has.service;

import homes.has.domain.Building;
import homes.has.domain.Review;
import homes.has.domain.ReviewBody;
import homes.has.domain.ReviewGrade;
import homes.has.repository.BuildingRepository;
import homes.has.repository.MemberRepository;
import homes.has.repository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class ReviewServiceTest{
    @Autowired
    private ReviewService reviewService;

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void createReviewTest () {
        // given
        String location = "진주대로 500번길";
        ReviewGrade grade = new ReviewGrade(5, 3, 2, 4);
        ReviewBody body = new ReviewBody("볕이 잘 들고... 뷰  가 조타...ㅎㅎ", "건물 옆에 커다란 느티나무가 있는데요.. 그래서인지 벌레가 너무 많이 꼬임...", "벽지가  넘무 파래여 집에 있으면 창백해보임니다");

        String location2 = "진주대로 550번길";
        ReviewGrade grade2 = new ReviewGrade(4, 4, 1, 4);
        ReviewBody body2 = new ReviewBody("주인장이 뿌링클을 사주심니다 ...냠냠..", "학교랑 넘나리 먹어요  ", "몰...루");

        // when
        reviewService.CreateReview(location, grade, body,37.1,128.2);
        reviewService.CreateReview(location2, grade2, body2,37.2,125.2);

        // then
        Building building = buildingRepository.findByName(location);
        assertNotNull(building);
        assertEquals(location, building.getName());

        //빌딩에서 리부 리스트 뽑아오는 기능 테스트 ..꼽
        List<Review> Reviews = reviewService.GetReviewList(location);
        assertEquals(1, Reviews.size());
        assertEquals(5,Reviews.get(0).getGrade().getLessor());
        List<Review> Reviews2 = reviewService.GetReviewList("메롱시티 어떤 버스정류장");
        assertEquals(null,Reviews2);

    }


    @Test
    void Delete_UpdateReviewTest () {
        // given
        String location3 = "진주대로 570번길";
        ReviewGrade grade3 = new ReviewGrade(5, 3, 2, 4);
        ReviewBody body3 = new ReviewBody("볕이 잘 들고... 뷰  가 조타...ㅎㅎ", "건물 옆에 커다란 느티나무가 있는데요.. 그래서인지 벌레가 너무 많이 꼬임...", "벽지가  넘무 파래여 집에 있으면 창백해보임니다");

        String location4 = "진주대로 550번길";
        ReviewGrade grade4 = new ReviewGrade(4, 4, 3, 4);
        ReviewBody body4 = new ReviewBody("주인장이 뿌링클을 사주심니다 ...냠냠..", "학교랑 넘나리 먹어요  ", "몰...루");

        // when
        reviewService.CreateReview(location3, grade3, body3,37.12,128.22);
        reviewService.CreateReview(location4, grade4, body4,37.2,125.21);

        // then
        reviewService.DeleteReview(2L);
        assertEquals(buildingRepository.count(),3);
        assertEquals(buildingRepository.findByName("진주대로 550번길").getPosx(),37.2);
        ReviewGrade grade5 = new ReviewGrade(5, 4, 5, 5);
        ReviewBody body5 = new ReviewBody("집이 신축이라 깔끔하고 위치도 학교에서 가까워서 조와", "냉장고가 넘작아요~~~~ ", "몰...루");

        reviewService.UpdateReview(3L,grade5,body5);
        Building building = buildingRepository.findByName(location3);
        assertEquals(building.getName(),location3);
        assertEquals(building.getTotalgrade(),19);
    }

    @Test
    void BoundingBox() {
        double latitude = 37.5;
        double longitude = 127;
        double distance = 10.0;

        double[] boundingBox = reviewService.getBoundingBox(latitude, longitude, distance);
        assertEquals(37.4, boundingBox[0], 1); //참값 37.41006...
        assertEquals(127, boundingBox[1], 1); //참값 126.88664...
        assertEquals(37.6, boundingBox[2], 1); //참값 37.5899...
        assertEquals(128, boundingBox[3], 1);
    }

    @Test
    public void BuildingByLocation() {
        // given
        double latitude = 37.12;
        double longitude = 128.22;
        double distance = 10.00;

        // when
        List<Building> buildings = reviewService.GetBuildingsByLocation(latitude, longitude, distance);

        // then
       assertEquals(buildings.size(),1);
    }
}