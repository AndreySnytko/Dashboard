package Dashboard;

import org.apache.commons.configuration.Configuration;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Properties;

public class Visitors implements Info{

    private String name="Счетчик посещений";
    private float count;
//    private Properties properties;
    private Configuration config;

    public Visitors() {
      this.count=1;
    }

    public Visitors(Configuration config) {
        this.config=config;
        getData();
    }

    public String getName(){
        return this.name;
    }

    public void getData(){
        Mongo mongo;
        mongo = new Mongo(config);

        if(mongo.getError()==0) {
            mongo.update();
            count = mongo.getCounterValue();
            mongo.close();
        }else{
            count=0;
        }

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

