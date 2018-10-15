package Dashboard;

import org.apache.commons.configuration.Configuration;
import java.util.ArrayList;
public class Visitors implements Info{

    private static final String NAME ="Счетчик посещений";
    private float count;
    private Configuration config;

    public Visitors() {
      this.count=1;
    }

    public Visitors(Configuration config) {
        this.config=config;
        getData();
    }

    public String getName(){
        return NAME;
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
        ArrayList<Float> values=new ArrayList<>();
        values.add(count);
        return values;
    }


}

