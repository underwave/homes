package homes.has.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FavoriteBuildingsDto{
    private Long id;
    private String location;
    private Double totalGrade = 0.0;
    private Integer reviewCount =0;
    private LocalDateTime createdAt;
    private Boolean isLiked;

    public FavoriteBuildingsDto(Long id, String name, Double totalGrade, int reviewCount, boolean isLiked, LocalDateTime createdAt) {
        this.id = id;
        this.location = name;
        this.totalGrade = totalGrade;
        this.reviewCount = reviewCount;
        this.isLiked = isLiked;
        this.createdAt= createdAt;
    }
}

