package yerong.wedle.category.news.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yerong.wedle.category.news.domain.News;
import yerong.wedle.category.news.dto.NewsResponse;
import yerong.wedle.category.news.exception.NewsNotFoundException;
import yerong.wedle.category.news.repository.NewsRepository;
import yerong.wedle.university.domain.University;
import yerong.wedle.university.exception.UniversityNotFoundException;
import yerong.wedle.university.repository.UniversityRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class NewsService {

    private final NewsRepository newsRepository;

    @Transactional(readOnly = true)
    public NewsResponse getNewsByNewsId(Long newsId) {
        News news = newsRepository.findById(newsId).orElseThrow(NewsNotFoundException::new);
        return convertToDto(news);
    }

    @Transactional(readOnly = true)
    public List<NewsResponse> getNews() {
        List<News> newsList = newsRepository.findAllByOrderByPublishedDateDesc();

        return newsList.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private NewsResponse convertToDto(News news) {
        return new NewsResponse(
                news.getNewsId(),
                news.getTitle(),
                news.getLink(),
                news.getPublishedDate(),
                news.getAdmissionYear(),
                news.getSource()
        );
    }
}