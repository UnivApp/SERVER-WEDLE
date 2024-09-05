package yerong.wedle.category.restaurant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yerong.wedle.category.restaurant.domain.MenuItem;
import yerong.wedle.category.restaurant.domain.Restaurant;
import yerong.wedle.category.restaurant.domain.RestaurantPhoto;
import yerong.wedle.category.restaurant.dto.MenuItemResponse;
import yerong.wedle.category.restaurant.dto.RestaurantPhotoResponse;
import yerong.wedle.category.restaurant.dto.RestaurantResponse;
import yerong.wedle.category.restaurant.repository.RestaurantRepository;
import yerong.wedle.university.domain.University;
import yerong.wedle.university.exception.UniversityNotFoundException;
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
                .orElseThrow(UniversityNotFoundException::new);
        List<Restaurant> restaurants = restaurantRepository.findByUniversity(university);
        return restaurants.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private RestaurantResponse convertToDto(Restaurant restaurant) {
        List<MenuItemResponse> menuItems = restaurant.getMenuItems().stream()
                .map(this::convertToMenuItemDto)
                .collect(Collectors.toList());

        List<RestaurantPhotoResponse> photos = restaurant.getPhotos().stream()
                .map(this::convertToPhotoDto)
                .collect(Collectors.toList());

        return new RestaurantResponse(
                restaurant.getName(),
                restaurant.getDescription(),
                restaurant.getLocation(),
                restaurant.getUrl(),
                menuItems,
                photos
        );
    }

    private MenuItemResponse convertToMenuItemDto(MenuItem menuItem) {
        return new MenuItemResponse(
                menuItem.getName(),
                menuItem.getPrice(),
                menuItem.getDescription(),
                menuItem.getCategory().getDisplayName(),
                menuItem.getImageUrl()
        );
    }

    private RestaurantPhotoResponse convertToPhotoDto(RestaurantPhoto restaurantPhoto) {
        return new RestaurantPhotoResponse(
                restaurantPhoto.getPhotoUrl()
        );
    }
}