package homes.has.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor//(access = AccessLevel.PROTECTED)
public class Building{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; //도로명주소

    /*@Embedded
     private ReviewGrade reviewGrade;*/
    private Double totalgrade = 0.0;

    @OneToMany(mappedBy = "building")
    private List<Favorite> favorites = new ArrayList<>();

    @OneToMany(mappedBy = "building")
    private List<Review> reviews = new ArrayList<>();

}
