package yerong.wedle.category.event.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArtistTop10Response {
    private String name;
    private String subname;
    private String image;
    private Integer count;
}