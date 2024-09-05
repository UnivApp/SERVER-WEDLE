package yerong.wedle.category.news.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yerong.wedle.category.news.domain.News;
import yerong.wedle.category.news.dto.NewsResponse;
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
    private final UniversityRepository universityRepository;

    @Transactional
    public List<NewsResponse> getNewsByUniversityName(String universityName) {
        University university = universityRepository.findByName(universityName)
                .orElseThrow(UniversityNotFoundException::new);
        List<News> news = newsRepository.findByUniversity(university);
        return news.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private NewsResponse convertToDto(News news) {
        return new NewsResponse(
                news.getTitle(),
                news.getLink()
        );
    }
}