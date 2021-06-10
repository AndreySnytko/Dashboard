package Dashboard;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Forecast {
    String date;
    String mintemp;
    String maxtemp;
    String avgtemp;
}
