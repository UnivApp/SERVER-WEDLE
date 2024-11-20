package yerong.wedle.category.event.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yerong.wedle.category.event.dto.ArtistTop10Response;
import yerong.wedle.category.event.service.ArtistService;

@Tag(name = "Artist API", description = "아티스트 정보 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/artists")
public class ArtistController {

    private final ArtistService artistService;

    @Operation(
            summary = "가장 많이 방문한 아티스트 조회",
            description = "대학교에서 가장 많이 방문한 아티스트 TOP 10을 조회합니다."
    )
    @GetMapping("/top")
    public ResponseEntity<List<ArtistTop10Response>> getTopArtists() {
        List<ArtistTop10Response> topArtists = artistService.getTopArtists();
        return ResponseEntity.ok(topArtists);
    }
}
