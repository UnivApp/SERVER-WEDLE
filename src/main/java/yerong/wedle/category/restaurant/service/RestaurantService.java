package yerong.wedle.category.restaurant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yerong.wedle.category.restaurant.domain.Restaurant;
import yerong.wedle.category.restaurant.dto.RestaurantResponse;
import yerong.wedle.category.restaurant.repository.RestaurantRepository;
import yerong.wedle.university.domain.University;
import yerong.wedle.university.repository.UniversityRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final UniversityRepository universityRepository;

    @Transactional
    public List<RestaurantResponse> getRestaurantsByUniversityName(String universityName) {
        University university = universityRepository.findByName(universityName)
                .orElseThrow(() -> new IllegalArgumentException("대학교를 찾을 수 없습니다."));
        List<Restaurant> restaurants = restaurantRepository.findByUniversity(university);
        return restaurants.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private RestaurantResponse convertToDto(Restaurant restaurant) {
        return new RestaurantResponse(
                restaurant.getName(),
                restaurant.getDescription(),
                restaurant.getLocation()
        );
    }
}