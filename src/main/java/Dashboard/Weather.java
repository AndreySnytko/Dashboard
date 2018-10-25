package Dashboard;

import org.apache.commons.configuration.Configuration;
import java.util.ArrayList;
import java.util.List;

public class Weather implements Info{

    private static final String NAME ="Погода";
    private String city;
    private String displayCity;
    private ArrayList<String> cities;//TODO: Переделать на enum
    private float currentTemp=0;
    private float tomorrowTemp=0;

    private String errorText="Не смог получить данные с сервера";
    private int error=1;

    private Configuration config;


    public Weather(Configuration config) {
        this.config=config;
        this.displayCity="Новосибирск";
        this.city="Novosibirsk";
        getData(displayCity);
    }

    public Weather(String displayCity) {
        this.displayCity = displayCity;
        getData(displayCity);
    }

    public String getName(){
        return NAME;
    }

    public List<String> getLabels(){
        List<String> labels=new ArrayList<>();
        labels.add("Сейчас");
        labels.add("Завтра");
        return labels;
    }

    public void getData(String displayCity){

        switch(displayCity){
            case "Новосибирск":this.city="Novosibirsk";break;
            case "Москва":this.city="Moscow";break;
            case "Санкт-Петербург":this.city="Saint%20Petersburg";break;
            default:this.city="Novosibirsk";break;
        }

        String url=config.getString("weatherURL")+this.city;
        HttpsInterface http = new HttpsInterface(url);

        if(http.getError()==0){   //Если данные с сервера получили

            String strXml=http.sendRequest();
            XML xml=new XML(strXml);

            if(xml.getError()==0){ //Удалось преобразовать в xml

                String tempNow = xml.getFirstXmlElement("/root/current", "temp_c");
                List<String> tempTomorrow = xml.getXmlElements("/root/forecast/forecastday", "maxtemp_c");

                if(xml.getError()==0) { //Атрибуты найдены
                    this.currentTemp = Float.parseFloat(tempNow);
                    this.tomorrowTemp = Float.parseFloat(tempTomorrow.get(1));//У нас прогноз на 2 дня, 2й день это следующий за текущим, поэтому берём 2е значение.
                    error=0;
                }
            }
        }

        this.cities=new ArrayList<String>();
        this.cities.add("Новосибирск"); this.cities.add("Москва"); this.cities.add("Санкт-Петербург");
    }



    public List<String> getComboList(){
        return this.cities;
    }

    public List<Float> getValues() {
        List<Float> values=new ArrayList<>();
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

