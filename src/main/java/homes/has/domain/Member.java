package homes.has.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Valid valid;

    private String location;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sns_key")
    private SnsMember snsMember;

    @OneToMany(mappedBy = "member")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy= "member")
    private List<Favorite> favorites = new ArrayList<>();

    @OneToMany(mappedBy= "member")
    private List<LocRequest> requests = new ArrayList<>();

}
