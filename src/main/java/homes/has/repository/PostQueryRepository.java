package homes.has.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import homes.has.enums.Category;
import homes.has.dto.PostDto;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static homes.has.domain.QPost.post;

@Repository
public class PostQueryRepository {

    private final JPAQueryFactory query;


    public PostQueryRepository(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }


    public List<PostDto> findPostByTitleAndBodyInCategoryContaining(PostSearchCond cond){

        String word = cond.getWord();
        Category category = cond.getCategory();

        return query.select(Projections.bean(PostDto.class,
                        post.member,post.id,post.category,post.id
                                ,post.title,post.body,post.likes,post.comments))
                .from(post)
                .where(searchCondition(word), isCategory(category))
                .fetch();

    }
    private BooleanBuilder searchCondition(String word) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (StringUtils.hasText(word))
            booleanBuilder.or(post.body.contains(word));
        if (StringUtils.hasText(word))
            booleanBuilder.or(post.title.contains(word));
        return booleanBuilder;
    }


    private BooleanExpression isCategory(Category category){
        return category!=null ? post.category.eq(category): null;
    }





}
