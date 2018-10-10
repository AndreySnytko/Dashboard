package Dashboard;

import java.util.ArrayList;

public class Weather implements Info{

    private String city;
    private ArrayList<String> cities;
    private float currentTemp;
    private float tomorrowTemp;

    //TODO: добавить конструктор с URLом
    public Weather() {
      getData();
    }


    public void getData(){
        this.city="Новосибирск";
        this.currentTemp=10;
        this.tomorrowTemp=11;
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

