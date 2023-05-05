package homes.has.service;

import homes.has.domain.Building;
import homes.has.domain.Favorite;
import homes.has.domain.Member;
import homes.has.domain.Valid;
import homes.has.repository.BuildingRepository;
import homes.has.repository.FavoriteRepository;
import homes.has.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
@Rollback(value = false)
class FavoriteServiceTest{

    @Autowired
    private FavoriteRepository favoriteRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private BuildingRepository buildingRepository;
    @Autowired
    private FavoriteService favoriteService;

    @Test
    public void testFavorite(){
        //given
        Member member = new Member();
        member.setLocation("진주대로 99");
        member.setValid(Valid.CERTIFIED);
        memberRepository.save(member);

        Building building = new Building();
        building.setName("진주대로 303");
        building.setPosx(37);
        building.setPosy(128);
        buildingRepository.save(building);

        //whern
        favoriteService.CreateFavorite(building,member);

        //then
        Favorite favorite = favoriteRepository.findByBuildingAndMember(building,member);
        assertEquals(member.getId(), favorite.getMember().getId());
        assertEquals(building.getId(), favorite.getBuilding().getId());

    }
    @Test
    public void testFavorite2(){
        //given
        Member member = new Member();
        member.setLocation("진주대로 991");
        member.setValid(Valid.CERTIFIED);
        memberRepository.save(member);

        Building building = new Building();
        building.setName("진주대로 3031");
        building.setPosx(37);
        building.setPosy(128);
        buildingRepository.save(building);

        //whern
        favoriteService.CreateFavorite(building,member);
        favoriteService.DeleteFavorite(building,member);

        //then
        assertNull(favoriteRepository.findByBuildingAndMember(building,member));


    }

}