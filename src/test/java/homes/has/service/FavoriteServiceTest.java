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

        Member member2 = new Member();
        member2.setLocation("진주대로 98");
        member2.setValid(Valid.CERTIFIED);
        memberRepository.save(member2);

        Building building = new Building();
        building.setName("진주대로 303");
        building.setPosx(37);
        building.setPosy(128);
        buildingRepository.save(building);

        Building building2 = new Building();
        building2.setName("진주대로 313");
        building2.setPosx(37.6);
        building2.setPosy(127);
        buildingRepository.save(building2);

        // when
        favoriteService.CreateFavorite(building,member);
        favoriteService.CreateFavorite(building,member2);
        favoriteService.CreateFavorite(building2,member);

        // then
        Favorite favorite = favoriteRepository.findByBuildingAndMember(building,member);
        assertEquals(member.getId(), favorite.getMember().getId());
        assertEquals(building.getId(), favorite.getBuilding().getId());
        assertEquals(2, favoriteService.GetBuildingFavorites(building.getId()));
        assertEquals(2,favoriteService.GetFavoriteBuildings(member.getId()).size());
    }
    @Test
    public void testFavorite2(){
        //given
        Member member = new Member();
        member.setLocation("진주대로 991");
        member.setValid(Valid.CERTIFIED);
        memberRepository.save(member);

        Member member2 = new Member();
        member2.setLocation("진주대로 98");
        member2.setValid(Valid.CERTIFIED);
        memberRepository.save(member2);

        Building building = new Building();
        building.setName("진주대로 3031");
        building.setPosx(37);
        building.setPosy(128);
        buildingRepository.save(building);

        //whern
        favoriteService.CreateFavorite(building,member);
        favoriteService.CreateFavorite(building,member2);
        favoriteService.DeleteFavorite(building,member);

        //then
        assertNull(favoriteRepository.findByBuildingAndMember(building,member));
        assertEquals(1, favoriteService.GetBuildingFavorites(building.getId()));
        assertEquals(0,favoriteService.GetFavoriteBuildings(member.getId()).size());

    }

}