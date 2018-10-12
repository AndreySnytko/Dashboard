package Dashboard;

import java.util.ArrayList;
import java.util.Properties;

public class Visitors implements Info{

    private String name="Счетчик посещений";
    private float count;
    private Properties properties;


    public Visitors() {
      this.count=1;
    }
    public Visitors(Properties properties) {
        this.properties=properties;
        getData();
    }

    public String getName(){
        return this.name;
    }

    public void getData(){

    }

    public ArrayList<String> getComboList(){
        return null;
    }

    public ArrayList<Float> getValues() {
        ArrayList<Float> values=new ArrayList<Float>();
        values.add(count);
        return values;
    }


}

