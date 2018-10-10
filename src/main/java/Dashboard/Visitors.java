package Dashboard;

import java.util.ArrayList;

public class Visitors implements Info{

    private String name="Счетчик посещений";

    private float count;


    public Visitors() {
      getData();
    }

    public String getName(){
        return this.name;
    }

    public void getData(){
        this.count=1;
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

