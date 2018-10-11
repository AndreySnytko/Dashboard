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
        HttpsInterface httpsGet = new HttpsInterface("https://www.cbr.ru/scripts/XML_daily.asp");
        String strXml=httpsGet.sendRequest();

        //TODO: Проверить на корректность strXml, может вернуть что страница не найдена, результат при этом будет отличным от пустой строки
        XML xml=new XML(strXml);

        //TODO: Добавить проверку на null (usd,eur)
        String usd = xml.getFirstXmlElement("/ValCurs/Valute[@ID=\"R01235\"]", "Value");
        String eur = xml.getFirstXmlElement("/ValCurs/Valute[@ID=\"R01239\"]", "Value");

        //TODO: Обернуть в try/catch, может возникнуть ситуация когда не сможет распарсить строку в float
        this.usd= Float.parseFloat(usd.replace(',','.'));
        this.eur= Float.parseFloat(eur.replace(',','.'));
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

