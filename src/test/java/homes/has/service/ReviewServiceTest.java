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
        String location = "진주대로 500번길 ";
        ReviewGrade grade = new ReviewGrade(5, 3, 2, 4);
        ReviewBody body = new ReviewBody("볕이 잘 들고... 뷰  가 조타...ㅎㅎ", "건물 옆에 커다란 느티나무가 있는데요.. 그래서인지 벌레가 너무 많이 꼬임...", "벽지가  넘무 파래여 집에 있으면 창백해보임니다");

        String location2 = "진주대로 550번길 ";
        ReviewGrade grade2 = new ReviewGrade(4, 4, 1, 4);
        ReviewBody body2 = new ReviewBody("주인장이 뿌링클을 사주심니다 ...냠냠..", "학교랑 넘나리 먹어요  ", "몰...루");

        // when
        reviewService.CreateReview(location, grade, body);
        reviewService.CreateReview(location2, grade2, body2);

        // then
        Building building = buildingRepository.findByName(location);
        assertNotNull(building);
        assertEquals(location, building.getName());

        //assertEquals(2, building.getReviews().size());

    }
}