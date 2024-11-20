package yerong.wedle.category.event.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yerong.wedle.category.event.domain.Artist;
import yerong.wedle.category.event.domain.Festival;
import yerong.wedle.category.event.domain.FestivalArtist;
import yerong.wedle.category.event.dto.ArtistResponse;
import yerong.wedle.category.event.dto.FestivalResponse;
import yerong.wedle.category.event.dto.FestivalWithArtistsResponse;
import yerong.wedle.category.event.dto.UniversityFestivalResponse;
import yerong.wedle.category.event.repository.FestivalRepository;
import yerong.wedle.university.domain.University;
import yerong.wedle.university.exception.UniversityNotFoundException;
import yerong.wedle.university.repository.UniversityRepository;

@RequiredArgsConstructor
@Service
public class FestivalService {

    private final FestivalRepository eventRepository;
    private final UniversityRepository universityRepository;

    @Transactional
    public UniversityFestivalResponse getFestivalsByUniversityId(Long universityId) {
        University university = universityRepository.findById(universityId)
                .orElseThrow(UniversityNotFoundException::new);

        List<FestivalResponse> festivalResponses = university.getFestivals().stream()
                .map(this::convertToFestivalResponse)
                .collect(Collectors.toList());

        return new UniversityFestivalResponse(
                university.getUniversityId(),
                university.getName(),
                festivalResponses
        );
    }

    private FestivalResponse convertToFestivalResponse(Festival festival) {
        List<FestivalWithArtistsResponse> dayLineup = festival.getFestivalArtists().stream()
                .collect(Collectors.groupingBy(
                        FestivalArtist::getFestivalDay,
                        Collectors.mapping(this::convertToArtistResponse, Collectors.toList())
                ))
                .entrySet().stream()
                .map(entry -> new FestivalWithArtistsResponse("Day" + entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
        return new FestivalResponse(
                festival.getName(),
                String.valueOf(festival.getFestivalYear()),
                festival.getDate(),
                dayLineup
        );
    }

    private ArtistResponse convertToArtistResponse(FestivalArtist festivalArtist) {
        Artist artist = festivalArtist.getArtist();
        return new ArtistResponse(
                artist.getName(),
                artist.getSubname(),
                artist.getImage()
        );
    }
}
