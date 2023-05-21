package homes.has.dto;

import lombok.*;

@NoArgsConstructor
@Getter
public class FileDto {
    private Long id;
    private String originFileName;
    private String fullPath;

//    public File toEntity() {
//        return File.builder()
//                .id(this.id)
//                .originFileName(this.originFileName)
//                .fullPath(this.fullPath)
//                .build();
//    }

    @Builder
    public FileDto(Long id, String originFileName, String fullPath) {
        this.id = id;
        this.originFileName = originFileName;
        this.fullPath = fullPath;
    }
}
