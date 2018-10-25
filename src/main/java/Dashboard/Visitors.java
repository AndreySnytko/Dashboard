package Dashboard;

import org.apache.commons.configuration.Configuration;
import java.util.ArrayList;
import java.util.List;

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

    public List<String> getComboList(){
        return null;
    }
    public List<String> getLabels(){ return null; }

    public List<Float> getValues() {
        List<Float> values=new ArrayList<>();
        values.add(count);
        return values;
    }


}

