package homes.has.service;

//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.model.CannedAccessControlList;
//import com.amazonaws.services.s3.model.PutObjectRequest;
import homes.has.domain.*;
import homes.has.dto.BuildingsDto;

import homes.has.enums.FilePath;
import homes.has.enums.Valid;
import homes.has.repository.BuildingRepository;
import homes.has.repository.FavoriteRepository;
import homes.has.repository.MemberRepository;
import homes.has.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final BuildingRepository buildingRepository;
    private final FavoriteRepository favoriteRepository;
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final ImageFileService imageFileService;
    private final ReviewImageFileService reviewImageFileService;

   // private final AmazonS3 amazonS3;
    private static final double EARTH_RADIUS = 6371; // 지구 반경(km)
    //private static final String UPLOAD_DIR = "/path/"; // 로컬에서 경로
    private static final String BUCKET_NAME = "homes-admin"; // S3버킷 이름
    /**
     * 리뷰 생성
     **/
    public void CreateReview (Long memberId, String location, ReviewGrade grade, ReviewBody body, double posx, double posy) throws IOException {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("멤버를 찾을 수 없음"));
        Building building = buildingRepository.findByName(location);
        if (building == null) {//빌딩 테이블에 location이 존재하지 않으면 추가
            building = new Building(location,posx,posy);
        }

        int countReview = building.getReviews().size(); //빌딩이 가지고있는 리뷰 수 확인
        Double newTotalGrade = updateTotalGrade(building.getTotalgrade(), countReview,null, grade);
        building.setTotalgrade(newTotalGrade); //빌딩 테이블의 total grade 받아와서 새로운 review 반영

//        imageFileService.saveFile(file, FilePath.REVIEW)



        Review review = Review.builder()
                .grade(grade)
                .body(body)
                .building(building)
                .Location(building.getName())
                .member(member)
                .build();

//      review entity에 이미지 추가, imageFileService에서 entity를 가져오는과정, for 문 내부의
//      1,2line에서 해당 객체의 id 값이 null이 아닌지 확인 할 필요가 있음

//        for (MultipartFile multipartFile : files) {
//            ImageFile imageFile = imageFileService.saveFile(multipartFile, FilePath.REVIEW);
//            ReviewImageFile reviewImageFile = reviewImageFileService.save(new ReviewImageFile(review, imageFile));
//            review.getReviewImageFiles().add(reviewImageFile);
//        }
//

        reviewRepository.save(review);

        building.getReviews().add(review);

        buildingRepository.save(building);

//      member valid 변경
        memberService.changeValid(member, Valid.CERTIFIED);
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
     * 상세 리뷰 정보 출력
     **/
    public Review getReviewById(Long id) {
        return reviewRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없음"));
    }

    /**
     * 특정 건물 리뷰 LIST 반환
     **/
    public List<Review> GetReviewList(String location){
        Building building = buildingRepository.findByName(location);
        if (building == null) {
            throw new IllegalArgumentException("찾을수업슴..");
        }
        return building.getReviews();
    }

    /*
     * n KM 주변의 building 리스트 반환

    public List<Building> GetBuildingsByLocation(double latitude, double longitude, double distance) {
        List<Building> buildings = new ArrayList<>();
        double[] boundingBox = getBoundingBox(latitude, longitude, distance);
        buildings.addAll(buildingRepository.findByPosxBetweenAndPosyBetween(boundingBox[0], boundingBox[2], boundingBox[1], boundingBox[3]));
        //System.out.println(boundingBox[0] +" "+ boundingBox[2]+" "+ boundingBox[1]+" "+ boundingBox[3]);
        return buildings;
    } */

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


    public List<BuildingsDto> GetBuildingsForMap (double latitude, double longitude, double distance, Long memberid) {
        List<Building> buildings = new ArrayList<>();
        double[] boundingBox = getBoundingBox(latitude, longitude, distance);
        buildings.addAll(buildingRepository.findByPosxBetweenAndPosyBetween(boundingBox[0], boundingBox[2], boundingBox[1], boundingBox[3]));
        List<BuildingsDto> reviewDtos = new ArrayList<>();
        for (Building building : buildings) {
            int reviewCount = building.getReviews().size();
            boolean isLiked = favoriteRepository.existsByLocationAndMemberId(building.getName(),memberid);
            reviewDtos.add(new BuildingsDto(building.getId(), building.getName(), building.getPosx(), building.getPosy(), building.getTotalgrade(),reviewCount, isLiked));
        }
        return reviewDtos;
    }
}
