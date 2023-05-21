package homes.has.domain;


import homes.has.domain.timestamp.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageFile extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String originalFileName;  // 파일 원본명
    private String savedName;
    private String path;  // 파일 저장 경로
    private int size;

    @ManyToOne
    @JoinColumn(name="post_id")
    private Post post;


    @Builder
    public ImageFile(Long id, String originalFileName, String savedName, int size, String path) {
        this.id = id;
        this.originalFileName = originalFileName;
        this.savedName = savedName;
        this.size = size;
        this.path =path;
    }


}
