package capstone.market.profile_dto;

import capstone.market.domain.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class SearchFilterDto {
    /**
     카테고리,조회수,좋아요,최신순,키워드
    **/
    List<CategoryType> categories;

    Integer views;


    List<LocationType> locations;

    Integer likes;
    String sort;
    String keyword;

    List<DepartmentType> departments;
    TrackType track;
    CollegeType collegeType;


    Integer page ;
    Integer size = 20;

    Integer ys=7;

}