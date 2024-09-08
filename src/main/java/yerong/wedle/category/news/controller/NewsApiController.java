package yerong.wedle.category.news.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import yerong.wedle.category.news.dto.NewsResponse;
import yerong.wedle.category.news.service.NewsService;

import java.util.List;

@Tag(name = "News API", description = "대학교 뉴스 정보 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/news")
public class NewsApiController {

    private final NewsService newsService;

    @Operation(
            summary = "대학교 뉴스 조회", description = "대학교 이름을 이용해 해당 대학교의 최신 뉴스 목록을 조회합니다."
    )
    @GetMapping
    public ResponseEntity<List<NewsResponse>> getMousByUniversityName(@RequestParam String universityName) {
        List<NewsResponse> news = newsService.getNewsByUniversityName(universityName);
        return ResponseEntity.ok(news);
    }
}
