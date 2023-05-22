package homes.has.Controller;

import homes.has.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/map/favorite")
@RequiredArgsConstructor
public class FavoriteController{
    private final FavoriteService favoriteService;

    /**
     * 관심 건물 등록 (API no.8)
     **/
    @PostMapping
    public void createFavorite(@RequestParam String location, @RequestParam Long memberId) {
        favoriteService.CreateFavorite(location, memberId);
    }

    /**
     * 관심 건물 삭제 (API no.9)
     **/
    @DeleteMapping
    public void deleteFavorite(@RequestParam String location, @RequestParam Long memberId) {
        favoriteService.DeleteFavorite(location, memberId);
    }

}
