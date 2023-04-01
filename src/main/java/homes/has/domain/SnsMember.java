package homes.has.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
public class SnsMember {
    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String accessToken;

    private String provideId;

    @Enumerated(EnumType.STRING)
    private Provider provider;

    private String name;


}
