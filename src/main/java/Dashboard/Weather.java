package Dashboard;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;

public class Weather implements Info{

    private String name="Погода";
    private String city;
    private String displayCity;
    private ArrayList<String> cities;
    private float currentTemp=0;
    private float tomorrowTemp=0;

    private String errorText="Не смог получить данные с сервера";
    private int error=1;


    public Weather() {
        this.displayCity="Новосибирск";
        this.city="Novosibirsk";
        getData(displayCity);
    }

    public Weather(String displayCity) {
        this.displayCity = displayCity;
        getData(displayCity);
    }

    public String getName(){
        return this.name;
    }

    public void getData(String displayCity){

        switch(displayCity){
            case "Новосибирск":this.city="Novosibirsk";break;
            case "Москва":this.city="Moscow";break;
            case "Санкт-Петербург":this.city="Saint%20Petersburg";break;
            default:this.city="Novosibirsk";break;
        }

        HttpsInterface http = new HttpsInterface("http://api.apixu.com/v1/forecast.xml?key=dec6a405f5914a8bbe070116181110&q="+this.city+"&days=2");
//        HttpsInterface http = new HttpsInterface("http://api.apixu.com/v1/forecast.json?key=dec6a405f5914a8bbe070116181110&q="+this.city+"&days=2");

        if(http.getError()==0){   //Если данные с сервера получили

            String strXml=http.sendRequest();
            XML xml=new XML(strXml);

            if(xml.getError()==0){ //Удалось преобразовать в xml

                String tempNow = xml.getFirstXmlElement("/root/current", "temp_c");
                ArrayList<String> tempTomorrow = xml.getXmlElements("/root/forecast/forecastday", "maxtemp_c");

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



    public ArrayList<String> getComboList(){
        return this.cities;
    }

    public ArrayList<Float> getValues() {
        ArrayList<Float> values=new ArrayList<Float>();
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

