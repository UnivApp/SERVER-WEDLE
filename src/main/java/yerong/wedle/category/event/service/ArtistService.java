package yerong.wedle.category.event.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yerong.wedle.category.event.dto.ArtistTop10Response;
import yerong.wedle.category.event.repository.ArtistRepository;

@RequiredArgsConstructor
@Service
public class ArtistService {

    private final ArtistRepository artistRepository;

    public List<ArtistTop10Response> getTopArtists() {
        List<Object[]> topArtistsData = artistRepository.findTopArtists();

        List<ArtistTop10Response> artistResponses = topArtistsData.stream()
                .map(record -> {
                    String image = (record[3] != null) ? String.valueOf(record[3]) : "";
                    ArtistTop10Response response = new ArtistTop10Response(
                            String.valueOf(record[1]),
                            String.valueOf(record[2]),
                            image,

                            ((Number) record[4]).intValue()
                    );
                    return response;
                })
                .limit(10)
                .collect(Collectors.toList());

        return artistResponses;
    }
}