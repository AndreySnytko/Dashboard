package Dashboard;

import org.apache.commons.configuration.Configuration;

import java.util.ArrayList;

public class Currency implements Info{

    private static final String NAME ="Курсы валют";
    private float usd;
    private float eur;

    private String errorText="Не смог получить данные с сервера";
    private int error=1;

    private Configuration config;


    public Currency(Configuration config) {
        this.config=config;
        this.getData();

    }

    public String getName(){
        return NAME;
    }

    public void getData(){
        HttpsInterface http = new HttpsInterface(config.getString("currencyURL"));
        String strXml=http.sendRequest();
        if(http.getError()==0) {   //Если данные с сервера получили

            XML xml = new XML(strXml);

            if(xml.getError()==0) { //Удалось преобразовать в xml
                String usd = xml.getFirstXmlElement("/ValCurs/Valute[@ID=\"R01235\"]", "Value");
                String eur = xml.getFirstXmlElement("/ValCurs/Valute[@ID=\"R01239\"]", "Value");

                if(xml.getError()==0) { //Атрибуты найдены
                    try {
                        this.usd = Float.parseFloat(usd.replace(',', '.'));
                        this.eur = Float.parseFloat(eur.replace(',', '.'));
                        error=0;
                    } catch (NumberFormatException e) {
                        this.usd = (float) 0.0;
                        this.eur = (float) 0.0;
                    }
                }
            }

        }

    }

    public ArrayList<String> getComboList(){
        return null;
    }

    public ArrayList<Float> getValues() {
        ArrayList<Float> values=new ArrayList<>();
        values.add(usd);
        values.add(eur);
        return values;
    }

    public String getErrorText() {
        return errorText;
    }

    public int getError() {
        return error;
    }
}

