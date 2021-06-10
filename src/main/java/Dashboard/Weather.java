package Dashboard;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.configuration.Configuration;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class Weather implements Info {

    private static final String NAME = "Погода";
    private String city;
    private String displayCity;
    private ArrayList<String> cities;
    private float currentTemp = 0;
    private float tomorrowTemp = 0;

    private String errorText = "Не могу получить данные";
    private int error = 1;

    private Configuration config;
    final static Logger logger = Logger.getLogger(Weather.class);


    public Weather(Configuration config) {
        this.config = config;
        this.displayCity = "Новосибирск";
        this.city = "Novosibirsk";
        getData(displayCity);
    }

    public Weather(String displayCity) {
        this.displayCity = displayCity;
        getData(displayCity);
    }

    public String getName() {
        return NAME;
    }

    public List<String> getLabels() {
        List<String> labels = new ArrayList<>();
        labels.add("Сейчас");
        labels.add("Завтра");
        return labels;
    }

    public void getData(String displayCity) {


        switch (displayCity) {
            case "Новосибирск":
                this.city = "Novosibirsk";
                break;
            case "Москва":
                this.city = "Moscow";
                break;
            case "Санкт-Петербург":
                this.city = "Saint%20Petersburg";
                break;
            default:
                this.city = "Novosibirsk";
                break;
        }

        String url = config.getString("weatherURL") + this.city;
        HttpsInterface http = new HttpsInterface(url);

        if (http.getError() == 0) {   //Если данные с сервера получили

            String strXml = http.sendRequest();
            logger.info("Weather response: " + strXml);
            ObjectMapper mapper = new ObjectMapper();

            try {
                WeatherEntity weatherEntity = mapper.readValue(strXml, WeatherEntity.class);
                this.currentTemp = Float.parseFloat(weatherEntity.getCurrent().getTemperature());
                this.tomorrowTemp = Float.parseFloat(weatherEntity.getForecast().get(weatherEntity.getForecast().keySet().toArray()[0]).getAvgtemp());
                errorText = "";
                error = 0;


            } catch (JsonProcessingException e) {
                logger.info("Не смог распарсить JSON ответ с сервера погоды:"+url);
            }

        }

        this.cities = new ArrayList<>();
        this.cities.add("Новосибирск");
        this.cities.add("Москва");
        this.cities.add("Санкт-Петербург");
    }


    public List<String> getComboList() {
        return this.cities;
    }

    public List<Float> getValues() {
        List<Float> values = new ArrayList<>();
        values.add(currentTemp);
        values.add(tomorrowTemp);
        return values;
    }

    public int getError() {
        return error;
    }

    public String getErrorText() {
        return errorText;
    }

}

