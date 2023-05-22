package homes.has.service;

import homes.has.domain.Building;
import homes.has.domain.Favorite;
import homes.has.domain.Member;
import homes.has.enums.Valid;
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
        String location = "가좌대로 11번길 1";
        String location2 = "가좌대로 11번길 2";
        Long memberId = 1L;
        Long memberId2 = 2L;

        Member member = new Member();
        member.setId(memberId);
        Member member2 = new Member();
        member2.setId(memberId2);

       /*
        Favorite favorite = new Favorite();
        favorite.setLocation(location);
        favorite.setMember(member);

        Favorite favorite2 = new Favorite();
        favorite2.setLocation(location2);
        favorite2.setMember(member2);*/

        // when
        favoriteService.CreateFavorite(location,member.getId());
        favoriteService.CreateFavorite(location2,member.getId());
        favoriteService.CreateFavorite(location,member2.getId());

        // then
        Favorite favorite = favoriteRepository.findByLocationAndMember(location,member);
        assertEquals(member.getId(), favorite.getMember().getId());
        Favorite favorite2 = favoriteRepository.findByLocationAndMember(location,member2);
        assertEquals(member2.getId(), favorite2.getMember().getId());

        assertEquals(2, favoriteService.GetFavoriteBuildings(member.getId()).size());
    }

}