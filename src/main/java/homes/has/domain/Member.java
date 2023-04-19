package homes.has.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import homes.has.domain.timestamp.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Entity
@NoArgsConstructor()
public class Member extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Valid valid;

    private String location;





    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sns_key")
    private SnsMember snsMember;
    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<Post> posts = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<Comment> comments = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<Review> reviews = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy= "member")
    private List<Favorite> favorites = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy= "member")
    private List<LocRequest> requests = new ArrayList<>();

}
