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
    private static final double EARTH_RADIUS = 6371; // 지구 반경(km)

    /**
     * 리뷰 생성
     **/
    public void CreateReview (String location, ReviewGrade grade, ReviewBody body, double posx, double posy) {
        Building building = buildingRepository.findByName(location);
        if (building == null) {//빌딩 테이블에 location이 존재하지 않으면 추가
            building = new Building(location,posx,posy);
        }

        int countReview = building.getReviews().size(); //빌딩이 가직있는 리뷰 수 확인
        Double newTotalGrade = updateTotalGrade(building.getTotalgrade(), countReview,null, grade);
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

    /**
     * 리뷰 삭제
     **/
    public void DeleteReview(Long id){
        Review review = reviewRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없음"));

        Building building = review.getBuilding(); //리뷰에 포함된 빌딩 정보 수정

        double newTotalGrade = updateTotalGrade(building.getTotalgrade(), building.getReviews().size(), review.getGrade(), null); //null은 삭제된 리부
        building.setTotalgrade(newTotalGrade); // 새로운 총 별점으로 업데이트
        building.getReviews().remove(review);
        reviewRepository.delete(review); // 리뷰 삭제
    }

    /**
     * 리뷰 수정
     **/
    public void UpdateReview(Long id, ReviewGrade grade, ReviewBody body){
        Review review = reviewRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없음"));

        Building building = review.getBuilding();
        double newTotalGrade = updateTotalGrade(building.getTotalgrade(), building.getReviews().size(), review.getGrade(), grade);
        building.setTotalgrade(newTotalGrade); //빌딩 정보 수정

        review.setGrade(grade);
        review.setBody(body);
        reviewRepository.save(review); //리뷰 수정 완
    }

    /**
     * 특정 건물 리뷰 LIST 반환
     **/
    public List<Review> GetReviewList(String location){
        Building building = buildingRepository.findByName(location);
        return building == null ? null : building.getReviews();
    }

    /**
     * n KM 주변의 building 리스트 반환
     **/
    public List<Building> GetBuildingsByLocation(double latitude, double longitude, double distance) {
        List<Building> buildings = new ArrayList<>();
        double[] boundingBox = getBoundingBox(latitude, longitude, distance);
        buildings.addAll(buildingRepository.findByPosxBetweenAndPosyBetween(boundingBox[0], boundingBox[2], boundingBox[1], boundingBox[3]));
        //System.out.println(boundingBox[0] +" "+ boundingBox[2]+" "+ boundingBox[1]+" "+ boundingBox[3]);
        return buildings;
    }

    /**거리만큼 away한 위-경도값을 반환하는 메서드**/
    public static double[] getBoundingBox(double latitude, double longitude, double distance) {
        double radianDistance = distance / EARTH_RADIUS;
        double radianLatitude = Math.toRadians(latitude);
        double radianLongitude = Math.toRadians(longitude);

        double minLat = radianLatitude - radianDistance;
        double maxLat = radianLatitude + radianDistance;

        double deltaLongitude = Math.asin(Math.sin(radianDistance) / Math.cos(radianLatitude));
        double minLng = radianLongitude - deltaLongitude;
        double maxLng = radianLongitude + deltaLongitude;

        double[] boundingBox = {Math.toDegrees(minLat), Math.toDegrees(minLng), Math.toDegrees(maxLat), Math.toDegrees(maxLng)};
        return boundingBox;
    }

    /** 이것은 ... 리뷰 CRUD 기능에 쓰이는 메서드... **/
    private double updateTotalGrade(double totalGrade, int countReview, ReviewGrade oldGrade, ReviewGrade newGrade) {
        double sum = totalGrade * countReview;
        if (oldGrade != null) { //수정or삭제인 경우
            sum -= oldGrade.getLessor() + oldGrade.getArea() + oldGrade.getQuality() + oldGrade.getNoise();
        }
        if (newGrade != null) { //생성or수정인 경우
            sum += newGrade.getLessor() + newGrade.getArea() + newGrade.getQuality() + newGrade.getNoise();
        }
        return sum / (countReview + (newGrade == null ? 0 : 1) - (oldGrade == null ? 0 : 1)); //삭제이면 0-1, 생성이면 1-0, 수정이면 1-1
    }

}

