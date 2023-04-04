package homes.has.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SnsMember {
    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String accessToken;

    private String provideId;

    @Enumerated(EnumType.STRING)
    private Provider provider;

    private String name;


}
