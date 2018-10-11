package Dashboard;

import java.util.ArrayList;

public class Weather implements Info{

    private String name="Погода";
    private String city;
    private ArrayList<String> cities;
    private float currentTemp;
    private float tomorrowTemp;

    class Temperature {
        private float now;
        private float tomorrow;

    }

    //TODO: добавить конструктор с URLом
    public Weather() {
      getData();
    }

    public String getName(){
        return this.name;
    }

    public void getData(){

//        ParseURL pUrlNow=new ParseURL("https://www.gismeteo.ru/weather-novosibirsk-4690/now/");

        HttpsInterface httpsGet = new HttpsInterface("http://api.apixu.com/v1/forecast.xml?key=dec6a405f5914a8bbe070116181110&q=Novosibirsk&days=2");
        String strXml=httpsGet.sendRequest();
        XML xml=new XML(strXml);
        String nskNow = xml.getFirstXmlElement("/root/current", "temp_c");
        ArrayList<String> nskTomorrow = xml.getXmlElements("/root/forecast/forecastday", "maxtemp_c");

        this.city="Новосибирск";

        this.currentTemp = Float.parseFloat(nskNow);
        this.tomorrowTemp = Float.parseFloat(nskTomorrow.get(1));

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


}

