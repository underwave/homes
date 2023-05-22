package homes.has.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class PostImageFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @OneToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "ImageFile_id")
    private ImageFile imageFile;

    public PostImageFile(Post post, ImageFile imageFile) {
        this.post=post;
        this.imageFile= imageFile;
    }
}
