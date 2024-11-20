package yerong.wedle.category.event.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import yerong.wedle.category.event.domain.Artist;

public interface ArtistRepository extends JpaRepository<Artist, Long> {

    @Query("SELECT ea.artist.artistId AS artistId, ea.artist.name AS name, ea.artist.subname AS subname, ea.artist.image AS image, COUNT(ea.artist.artistId) AS visitCount "
            + "FROM FestivalArtist ea "
            + "GROUP BY ea.artist.artistId, ea.artist.name, ea.artist.subname, ea.artist.image "
            + "ORDER BY visitCount DESC")
    List<Object[]> findTopArtists();
}
