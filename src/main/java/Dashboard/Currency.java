package Dashboard;

import java.util.ArrayList;

public class Currency implements Info{

    private String name="Курсы валют";

    private float usd;
    private float eur;


    //TODO: добавить конструктор с URLом
    public Currency() {
      this.getData();

    }

    public String getName(){
        return this.name;
    }

    public void getData(){
        HttpsInterface httpsGetLc = new HttpsInterface("https://www.cbr.ru/scripts/XML_daily.asp");
//        HttpsInterface httpsGetLc = new HttpsInterface("http://www.cbr1.ru/scripts/XML_daily.asp");

        String strXml=httpsGetLc.sendRequest();
        //TODO: Проверить на корректность strXml, может вернуть что страница не найдена

        System.out.println("strXml: "+strXml);

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

