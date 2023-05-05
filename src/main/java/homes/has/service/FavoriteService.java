package homes.has.service;

import homes.has.domain.Building;
import homes.has.domain.Favorite;
import homes.has.domain.Member;
import homes.has.repository.BuildingRepository;
import homes.has.repository.FavoriteRepository;
import homes.has.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class FavoriteService{
    private final FavoriteRepository favoriteRepository;
    private final MemberRepository memberRepository;
    private final BuildingRepository buildingRepository;

    /**
     * 좋아하는 건물 등록
     **/
    public void CreateFavorite(Building building, Member member){
        Favorite favorite = favoriteRepository.findByBuildingAndMember(building, member);
        if (favorite != null){
            throw new IllegalArgumentException("이미 좋아요를 눌렀음");
        }

        favorite = Favorite.builder()
                .building(building)
                .member(member)
                .build();
        favoriteRepository.save(favorite);
    }

    /**
     * 좋아하는 건물 등록 취소
     **/
    public void DeleteFavorite (Building building, Member member) {
        Favorite favorite = favoriteRepository.findByBuildingAndMember(building, member);
        if (favorite == null) {
            throw new IllegalArgumentException("좋아요를 조회할 수 없음");
        }
        member.getFavorites().remove(favorite);
        building.getFavorites().remove(favorite);
        favoriteRepository.delete(favorite);
    }

    /**
     * 좋아요 누른 건물 조회
     **/
    public List<Building> GetFavoriteBuildings(Long memberid){
        Member member = memberRepository.findById(memberid).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없음"));
        return member.getFavorites().stream()
                .map(Favorite::getBuilding)
                .collect(Collectors.toList());
    }

    /**
     * 빌딩의 좋아요 개수 조회
     **/
    public int GetBuildingFavorites(Long buildingid){
        Building building = buildingRepository.findById(buildingid).orElseThrow(() -> new IllegalArgumentException("빌딩을 찾을 수 없음"));
        return building.getFavorites().size();
    }

}
