package homes.has.Controller;

import homes.has.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/map/favorite")
@RequiredArgsConstructor
public class FavoriteController{
    private final FavoriteService favoriteService;

}
