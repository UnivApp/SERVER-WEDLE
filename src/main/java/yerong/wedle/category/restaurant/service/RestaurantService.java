package yerong.wedle.category.restaurant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yerong.wedle.category.restaurant.domain.Hashtag;
import yerong.wedle.category.restaurant.domain.Restaurant;
import yerong.wedle.category.restaurant.dto.RestaurantResponse;
import yerong.wedle.category.restaurant.dto.TopRestaurantResponse;
import yerong.wedle.category.restaurant.exception.RestaurantNotFoundException;
import yerong.wedle.category.restaurant.repository.RestaurantRepository;
import yerong.wedle.university.domain.University;
import yerong.wedle.university.exception.UniversityNotFoundException;
import yerong.wedle.university.repository.UniversityRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final UniversityRepository universityRepository;

    @Transactional
    public List<RestaurantResponse> getRestaurantsByUniversityId(Long universityId) {
        University university = universityRepository.findById(universityId)
                .orElseThrow(UniversityNotFoundException::new);
        List<Restaurant> restaurants = restaurantRepository.findByUniversity(university)
                .stream()
                .sorted(Comparator.comparingInt(Restaurant::getRanking))
                .collect(Collectors.toList());

        return restaurants.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private RestaurantResponse convertToDto(Restaurant restaurant) {
        List<String> hashtags = restaurant.getHashtags().stream()
                .map(Hashtag::getName)
                .collect(Collectors.toList());

        return new RestaurantResponse(
                restaurant.getName(),
                restaurant.getLocation(),
                restaurant.getPlaceUrl(),
                hashtags,
                restaurant.getImageUrl()
        );
    }

    @Transactional
    public List<TopRestaurantResponse> getTopRestaurants() {
        List<University> universities = universityRepository.findAll();

        return universities.stream()
                .map(this::findTopRestaurantForUniversity)
                .filter(topRestaurant -> topRestaurant != null) // null 체크하여 결과에서 제외
                .collect(Collectors.toList());
    }

    private TopRestaurantResponse findTopRestaurantForUniversity(University university) {
        List<Restaurant> restaurants = restaurantRepository.findByUniversity(university);

        if (restaurants.isEmpty()) {
            return null;
        }

        // 순위가 1인 식당만 찾기
        Restaurant topRestaurant = restaurants.stream()
                .filter(restaurant -> restaurant.getRanking() == 1)
                .findFirst()
                .orElse(null); // 없으면 null 반환

        if (topRestaurant == null) {
            return null; // 순위가 1인 식당이 없으면 null 반환
        }

        return convertToTopDto(topRestaurant, university.getName());
    }

    private TopRestaurantResponse convertToTopDto(Restaurant restaurant, String universityName) {
        List<String> hashtags = restaurant.getHashtags().stream()
                .map(Hashtag::getName)
                .collect(Collectors.toList());

        String topMessage = universityName + " 1등 맛집";

        return new TopRestaurantResponse(
                restaurant.getName(),
                restaurant.getLocation(),
                restaurant.getPlaceUrl(),
                hashtags,
                restaurant.getImageUrl(),
                topMessage
        );
    }
}