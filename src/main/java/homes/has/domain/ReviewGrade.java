package homes.has.domain;


import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Embeddable
public class ReviewGrade {

    private int lessor;
    private int quality;
    private int area;
    private int noise;
}
