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

@Tag(name = "News API", description = "입시 기사 정보 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/news")
public class NewsApiController {

    private final NewsService newsService;

    @Operation(
            summary = "입시 기사 조회",
            description = "기사 ID를 이용해 해당 기사를 조회합니다."
    )
    @GetMapping("/{newsId}")
    public ResponseEntity<NewsResponse> getNewsByNewsId(@RequestParam Long newsId) {
        NewsResponse news = newsService.getNewsByNewsId(newsId);
        return ResponseEntity.ok(news);
    }

    @Operation(
            summary = "모든 대입 기사 조회",
            description = "모든 대입 기사를 조회합니다."
    )
    @GetMapping
    public ResponseEntity<List<NewsResponse>> getNews() {

        List<NewsResponse> news = newsService.getNews();
        return ResponseEntity.ok(news);
    }
}
