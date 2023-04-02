package homes.has.repository;


import homes.has.domain.Category;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class PostSearchCond {
    private Category category;
    private String word;

    public PostSearchCond(String word,Category category) {
        this.category = category;
        this.word = word;
    }

    public PostSearchCond(String word) {
        this.word = word;
    }
}
