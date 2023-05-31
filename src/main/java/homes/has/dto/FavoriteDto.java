package homes.has.dto;

import lombok.Getter;
import org.springframework.web.bind.annotation.RequestParam;
@Getter
public class FavoriteDto{
    private String location;
    private String memberId;
}
