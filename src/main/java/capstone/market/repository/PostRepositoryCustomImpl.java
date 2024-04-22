package capstone.market.repository;

import capstone.market.domain.*;
import capstone.market.profile_dto.PostDetailDto;
import capstone.market.profile_dto.SearchFilterDto;
import com.querydsl.core.BooleanBuilder;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Data
@Transactional(readOnly = true)
public class PostRepositoryCustomImpl implements PostRepositoryCustom {

    private final JPAQueryFactory queryFactory;

            // 기존 기능(QueryDSL) + 페이징
            @Override
            public List<PostDetailDto> searchFilterWithPaging(SearchFilterDto searchFilterDto) {

                QPost post = QPost.post;
                //QCategory category = QCategory.category;
                //QDepartment department = QDepartment.department;
                BooleanBuilder whereBuilder = new BooleanBuilder();
                Pageable pageable = PageRequest.of(searchFilterDto.getPage(), searchFilterDto.getSize());



                if (searchFilterDto.getTrack() != null && !searchFilterDto.getTrack().toString().isEmpty()) {
                    whereBuilder.and(post.track.eq(searchFilterDto.getTrack()));
                }

                if (searchFilterDto.getCollegeType() != null && !searchFilterDto.getCollegeType().toString().isEmpty()) {
                    whereBuilder.and(post.college.eq(searchFilterDto.getCollegeType()));

                }


                if (searchFilterDto.getCategories() != null && !searchFilterDto.getCategories().isEmpty()) {
                    BooleanExpression[] categoryExpressions = searchFilterDto.getCategories().stream()
                            .map(post.categoryType::eq)
                            .toArray(BooleanExpression[]::new);
                    whereBuilder.andAnyOf(categoryExpressions);
                }

                if (searchFilterDto.getViews() != null) {
                    whereBuilder.and(post.views.goe(searchFilterDto.getViews()));
                }
                if (searchFilterDto.getLikes() != null) {
                    whereBuilder.and(post.likes.goe(searchFilterDto.getLikes()));
                }
                //대소 문자 구분 x
/*                if (searchFilterDto.getKeyword() != null && !searchFilterDto.getKeyword().isEmpty()) {
                    whereBuilder.and(post.post_title.containsIgnoreCase(searchFilterDto.getKeyword())
                            .or(post.post_text.containsIgnoreCase(searchFilterDto.getKeyword())));
                }*/
                //대소문자 구분

//                if (searchFilterDto.getKeyword() != null && !searchFilterDto.getKeyword().isEmpty()) {
//                    whereBuilder.and(post.post_title.contains(searchFilterDto.getKeyword())
//                            .or(post.post_text.contains(searchFilterDto.getKeyword())));
//                }

                if (searchFilterDto.getKeyword() != null && !searchFilterDto.getKeyword().isEmpty()) {
                    whereBuilder.and(post.post_title.startsWith(searchFilterDto.getKeyword())
                            .or(post.post_text.startsWith(searchFilterDto.getKeyword())));
                }

//                if (searchFilterDto.getKeyword() != null && !searchFilterDto.getKeyword().isEmpty()) {
//                    whereBuilder.and(post.post_title.containsIgnoreCase(searchFilterDto.getKeyword()));
//
//                }

                if (searchFilterDto.getLocations() != null && !searchFilterDto.getLocations().isEmpty()) {
                    BooleanExpression[] categoryExpressions2 = searchFilterDto.getLocations().stream()
                            .map(post.locationType::eq)
                            .toArray(BooleanExpression[]::new);
                    whereBuilder.andAnyOf(categoryExpressions2);
                }



                if (searchFilterDto.getDepartments() != null && !searchFilterDto.getDepartments().isEmpty()) {
                    BooleanExpression[] categoryExpressions3 = searchFilterDto.getDepartments().stream()
                            .map(post.departmentType::eq)
                            .toArray(BooleanExpression[]::new);
                    whereBuilder.andAnyOf(categoryExpressions3);
                }



                    BooleanBuilder finalWhere = whereBuilder.and(post.purchased.isNull());

                    OrderSpecifier<?> order;
                    if (searchFilterDto.getSort() != null && searchFilterDto.getSort().equalsIgnoreCase("views")) {
                        order = post.views.desc();
                    } else if (searchFilterDto.getSort() != null && searchFilterDto.getSort().equalsIgnoreCase("likes")) {
                        order = post.likes.desc();
                    } else {
                        order = post.createdDate.desc();
                    }



                    // 페이징 처리
                    //long page_number = pageable.getOffset();

                    long page_number = searchFilterDto.getPage();
                    int page_size = pageable.getPageSize();

                    List<Post> filteredPosts = queryFactory
                            .selectFrom(post)
                            //.leftJoin(post.category).fetchJoin() //다대일
                            //.leftJoin(post.department,department).fetchJoin() //다대일
                            //.leftJoin(post.images).fetchJoin()
                            .where(finalWhere)  // 판매되지 않은 게시글 필터링
                            .orderBy(order)
                            .offset(page_number * page_size)
                            .limit(page_size)  // size 만큼의 데이터를 가져옴
                            .fetch();

                    List<PostDetailDto> SearchPosts = filteredPosts.stream()
                            .map(p -> new PostDetailDto(p))
                            .collect(Collectors.toList());



                    return SearchPosts;

                }


        }
