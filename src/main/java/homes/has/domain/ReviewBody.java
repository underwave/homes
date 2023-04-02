package homes.has.domain;


import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Embeddable
public class ReviewBody {
    private String advantage;
    private String weakness;
    private String etc;
}
