package Dashboard;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.HashMap;


@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Current {
    String observation_time;
    String temperature;
}
