package yerong.wedle.neis;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;

@Getter
public class NeisApiResponse {

    @JsonProperty("schoolInfo")
    private List<SchoolInfo> schoolInfo;

    public List<NeisSchoolResponse> getSchoolList() {
        if (schoolInfo != null && !schoolInfo.isEmpty()) {
            for (SchoolInfo info : schoolInfo) {
                if (info.getRow() != null) {
                    return info.getRow();
                }
            }
        }
        return null;
    }
}
