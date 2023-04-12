package homes.has.service;

import homes.has.domain.Building;
import homes.has.domain.Review;
import homes.has.domain.ReviewBody;
import homes.has.domain.ReviewGrade;
import homes.has.repository.BuildingRepository;
import homes.has.repository.MemberRepository;
import homes.has.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final BuildingRepository buildingRepository;

    /**
     * 리뷰 생성
     **/
    public void CreateReview (String location, ReviewGrade grade, ReviewBody body) {
        Building building = buildingRepository.findByName(location);
        if (building == null) { //빌딩 테이블에 location이 존재하지 않으면 추가
            building = new Building();
            building.setName(location);
        }

        Double totalGrade = building.getTotalgrade();
        int countReview = building.getReviews().size(); //빌딩이 가직있는 리뷰 수 확인
        Double newTotalGrade = UpdatetotalGrade(totalGrade, countReview, grade);
        building.setTotalgrade(newTotalGrade); //빌딩 테이블의 total grade 받아와서 새로운 review 반영

        Review review = new Review();
        review.setGrade(grade);
        review.setBody(body);

        review.setBuilding(building);
        reviewRepository.save(review);

       // building.getReviews().add(review);
        buildingRepository.save(building);
    }

    private Double UpdatetotalGrade (Double totalGrade, int countReview, ReviewGrade grade) {
        Double sum = totalGrade * countReview + (double) (grade.getLessor() + grade.getArea() + grade.getQuality() + grade.getNoise());
        return sum / (countReview + 1);
    } //업데이트할 별점 calculate 하는 메서드..


}

