package Dashboard;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class LocationEntity {
    String name;
    String country;
    String region;
    Current current;

}
