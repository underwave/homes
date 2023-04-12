package homes.has.domain;


import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@Embeddable
@NoArgsConstructor

public class ReviewGrade {
    private int lessor;
    private int quality;
    private int area;
    private int noise;

    public ReviewGrade (int lessor, int quality, int area, int noise) {
        this.lessor= lessor;
        this.quality=quality;
        this.area =area;
        this.noise=noise;
    }
}