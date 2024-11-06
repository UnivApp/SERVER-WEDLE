package yerong.wedle.category.restaurant.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import yerong.wedle.category.restaurant.dto.RestaurantResponse;
import yerong.wedle.category.restaurant.dto.KakaoApiDocument;
import yerong.wedle.category.restaurant.dto.KakaoApiResponse;
import yerong.wedle.university.domain.University;
import yerong.wedle.university.exception.UniversityNotFoundException;
import yerong.wedle.university.repository.UniversityRepository;

@Service
@RequiredArgsConstructor
public class KakaoSearchApiService {

    @Value("${kakao.client-id}")
    private String KAKAO_CLIENT_ID;

    private final RestTemplate restTemplate;
    private final UniversityRepository universityRepository;

    public List<RestaurantResponse> searchRestaurant(String universityName) {
        University university = universityRepository.findByName(universityName)
                .orElseThrow(UniversityNotFoundException::new);
        String query;
        if (university.getSubName() != null) {
            query = university.getSubName() + " 맛집";
        } else {
            query = universityName + " 맛집";
        }
        String url = "https://dapi.kakao.com/v2/local/search/keyword.json?query=" + query + "&size=15";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + KAKAO_CLIENT_ID);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<KakaoApiResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity, KakaoApiResponse.class);

        List<RestaurantResponse> restaurantResponses = new ArrayList<>();

        if (response.getBody() != null && response.getBody().getDocuments() != null) {
            for (KakaoApiDocument document : response.getBody().getDocuments()) {
                String fullCategoryName = document.getCategory_name();
                String lastCategory = null;

                if (fullCategoryName != null && !fullCategoryName.isEmpty()) {
                    String[] categories = fullCategoryName.split(" > ");
                    if (categories.length > 0) {
                        lastCategory = categories[categories.length - 1];
                    }
                }

                restaurantResponses.add(RestaurantResponse.builder()
                        .name(document.getPlace_name())
                        .roadAddressName(document.getRoad_address_name())
                        .addressName(document.getAddress_name())
                        .categoryName(lastCategory)
                        .phone(document.getPhone())
                        .placeUrl(document.getPlace_url())
                        .x(document.getX())
                        .y(document.getY())
                        .build());
            }
        }

        return restaurantResponses;
    }
}
