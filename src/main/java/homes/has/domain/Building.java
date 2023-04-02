package homes.has.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Entity
public class Building {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Embedded
    private ReviewGrade reviewGrade;

    @OneToMany(mappedBy = "building")
    private List<Favorite> favorites = new ArrayList<>();

    @OneToMany(mappedBy= "building")
    private List<Review> reviews = new ArrayList<>();

}
