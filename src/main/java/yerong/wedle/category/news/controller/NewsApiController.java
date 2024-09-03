package yerong.wedle.category.news.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import yerong.wedle.category.news.dto.NewsResponse;
import yerong.wedle.category.news.service.NewsService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/news")
public class NewsApiController {

    private final NewsService newsService;

    @GetMapping
    public ResponseEntity<List<NewsResponse>> getMousByUniversityName(@RequestParam String universityName) {
        List<NewsResponse> news = newsService.getNewsByUniversityName(universityName);
        return ResponseEntity.ok(news);
    }
}
