package homes.has.repository;

import homes.has.domain.Building;
import homes.has.domain.Favorite;
import homes.has.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite,Long>{
    Favorite findByBuildingAndMember(Building building, Member member);
}