package homes.has.service;

import homes.has.domain.Building;
import homes.has.domain.Favorite;
import homes.has.domain.Member;
import homes.has.repository.FavoriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class FavoriteService{
    private final FavoriteRepository favoriteRepository;

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
        favoriteRepository.delete(favorite);
    }
}
