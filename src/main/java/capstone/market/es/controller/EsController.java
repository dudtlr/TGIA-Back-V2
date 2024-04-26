package capstone.market.es.controller;


import capstone.market.es.dto.PostDocumentDTO;
import capstone.market.es.service.EsService;
import capstone.market.profile_dto.SearchFilterDto2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@Transactional
public class EsController {

    private final EsService esService;

    @GetMapping("/es_search")
    public List<PostDocumentDTO> search(
            @RequestParam(required = false) List<String> categories,
            @RequestParam(required = false) List<String> locations,
            @RequestParam(required = false) List<String> departments,
            @RequestParam(required = false) String track,
            @RequestParam(required = false) String collegeType,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "20") Integer size
    ) throws IOException {

        SearchFilterDto2 searchFilterDto = new SearchFilterDto2();

        if (categories != null && !categories.isEmpty()) {
            searchFilterDto.setCategories(categories);
        }
        if (locations != null && !locations.isEmpty()) {
            searchFilterDto.setLocations(locations);
        }
        if (departments != null && !departments.isEmpty()) {
            searchFilterDto.setDepartments(departments);
        }
        if (track != null) {
            searchFilterDto.setTrack(track);
        }
        if (collegeType != null) {
            searchFilterDto.setCollegeType(collegeType);
        }

        if (sort != null) {
            searchFilterDto.setSort(sort);
        }
        if (keyword != null) {
            searchFilterDto.setKeyword(keyword);
        }
        searchFilterDto.setPage(page);

        searchFilterDto.setSize(size);





        List<PostDocumentDTO> postDocuments = esService.search(searchFilterDto);

        return postDocuments;
    }
}