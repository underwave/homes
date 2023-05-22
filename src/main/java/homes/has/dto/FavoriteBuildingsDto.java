package homes.has.dto;

import lombok.Getter;

@Getter
public class FavoriteBuildingsDto{
    private Long id;
    private String location;
    private Double totalgrade = 0.0;
    private Integer reviewCount;
    private Boolean isLiked;

    public FavoriteBuildingsDto(Long id, String name, Double totalGrade, int reviewCount, boolean isLiked) {
        this.id = id;
        this.location = name;
        this.totalgrade = totalGrade;
        this.reviewCount = reviewCount;
        this.isLiked = isLiked;
    }
}

