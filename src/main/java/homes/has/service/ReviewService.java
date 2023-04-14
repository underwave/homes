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

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final BuildingRepository buildingRepository;

    /**
     * 리뷰 생성
     **/
    public void CreateReview (String location, ReviewGrade grade, ReviewBody body, double posx, double posy) {
        Building building = buildingRepository.findByName(location);
        if (building == null) {//빌딩 테이블에 location이 존재하지 않으면 추가
            building = new Building(location,posx,posy);
        }

        Double totalGrade = building.getTotalgrade();
        int countReview = building.getReviews().size(); //빌딩이 가직있는 리뷰 수 확인
        Double newTotalGrade = UpdatetotalGrade(totalGrade, countReview, grade);
        building.setTotalgrade(newTotalGrade); //빌딩 테이블의 total grade 받아와서 새로운 review 반영

        Review review = Review.builder()
                .grade(grade)
                .body(body)
                .building(building)
                .Location(building.getName())
                .build();
        reviewRepository.save(review);

        building.getReviews().add(review);
        buildingRepository.save(building);
    }


    private double UpdatetotalGrade (double totalGrade, int countReview, ReviewGrade grade) {
        double sum = totalGrade * countReview + (double) (grade.getLessor() + grade.getArea() + grade.getQuality() + grade.getNoise());
        return sum / (countReview + 1);
    } //업데이트할 별점 calculate 하는 메서드..

    /**
     * 마커 표시용 빌딩 반환(/map) -> 검토 필..
     **/
    public List<Building> getBuildingsByDistance(double posx, double posy, double radius) {
        List<Building> BuildingsAll = buildingRepository.findAll(); //빌딩다 끄집어와서...

        List<Building> buildingsIn = new ArrayList<>(); // 반경에 속하는 빌딩 리스트 생성
        for (Building building : BuildingsAll) {
            double buildingradiuskm = distance(posx,posy, building.getPosx(), building.getPosy()); //건물까지의 거리
            if (buildingradiuskm<=radius) { // 원점에서 건물 사이의 거리가 제시된 거리보다 작으면
                buildingsIn.add(building); //반경 내 빌딩 리스트에 빌딩 추가
            }
        }
        return buildingsIn;
    }

    //haversine formula
    private double distance(double zeroposx, double zeroposy, double posx, double posy) {
        double R = 6371; //지구 반지름
        double xDistance = Math.abs(Math.toRadians(posx - zeroposx));
        double yDistance = Math.abs(Math.toRadians(posy - zeroposy));

        double sinDeltaLat = Math.sin(xDistance / 2);
        double sinDeltaLng = Math.sin(yDistance / 2);
        double squareRoot = Math.sqrt(
                sinDeltaLat * sinDeltaLat +
                        Math.cos(Math.toRadians(zeroposx)) * Math.cos(Math.toRadians(posx)) * sinDeltaLng * sinDeltaLng);

        double distance = 2 * R * Math.asin(squareRoot);
        return distance;
    }

    /**
     * 특정 건물 리뷰 LIST 반환
     **/
    public List<Review> GetReviewList(String location){
        Building building = buildingRepository.findByName(location);
        return building == null ? null : building.getReviews();
    }
    /**
     * 리뷰 삭제
     **/
    public void DeleteReview(Long id){
        Review review = reviewRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없음"));

        Building building = review.getBuilding(); //리뷰에 포함된 빌딩 정보 수정
        building.getReviews().remove(review);
        double totalGrade = building.getTotalgrade();
        int countReview = building.getReviews().size(); //삭제 상태에서 size
        double newTotalGrade = BackUpdateTotalGrade(totalGrade, countReview, review.getGrade(), null); //null은 삭제된 리부

        building.setTotalgrade(newTotalGrade); // 새로운 총 별점으로 업데이트
        reviewRepository.delete(review); // 리뷰 삭제
    }

    private double BackUpdateTotalGrade (double totalGrade, int countReview, ReviewGrade grade, ReviewGrade nowgrade) {
        if (nowgrade==null){ //삭제
            return(totalGrade * (countReview+1) - (double) (grade.getLessor() + grade.getArea() + grade.getQuality() + grade.getNoise()));
        } else{ //수정
            double sum = totalGrade * countReview - ((double) (nowgrade.getLessor() + nowgrade.getArea() + nowgrade.getQuality() + nowgrade.getNoise()))
                    + ((double) (grade.getLessor() + grade.getArea() + grade.getQuality() + grade.getNoise()));
            return sum /(countReview);
        }
    }

    /**
     * 리뷰 수정
     **/
    public void UpdateReview(Long id, ReviewGrade grade, ReviewBody body){
        Review review = reviewRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없음"));

        Building building = review.getBuilding();
        double totalGrade = building.getTotalgrade();
        int countReview = building.getReviews().size();
        ReviewGrade nowGrade = review.getGrade();
        double newTotalGrade = BackUpdateTotalGrade(totalGrade, countReview, grade, nowGrade);
        building.setTotalgrade(newTotalGrade); //빌딩 정보 수정

        review.setGrade(grade);
        review.setBody(body);
        reviewRepository.save(review); //리뷰 수정 완
    }
}

