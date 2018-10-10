package Dashboard;

import java.util.ArrayList;

public class Currency implements Info{

    private String name="Курсы валют";

    private float usd;
    private float eur;


    //TODO: добавить конструктор с URLом
    public Currency() {
      getData();
    }

    public String getName(){
        return this.name;
    }

    public void getData(){
        this.usd= (float) 60.55;
        this.eur= (float) 70.05;
    }

    public ArrayList<String> getComboList(){
        return null;
    }

    public ArrayList<Float> getValues() {
        ArrayList<Float> values=new ArrayList<Float>();
        values.add(usd);
        values.add(eur);
        return values;
    }


}

