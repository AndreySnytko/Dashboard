package Dashboard;

import org.apache.commons.configuration.Configuration;
import java.util.ArrayList;
import java.util.List;

public class Currency implements Info{

    private static final String NAME ="Курсы валют";
    private float usd;
    private float eur;

    private String errorText="";
    private int error=1;

    private Configuration config;


    public Currency(Configuration config) {
        this.config=config;
        this.getData();

    }

    public String getName(){
        return NAME;
    }

    public List<String> getLabels(){
        List<String> labels=new ArrayList<>();
        labels.add("USD");
        labels.add("EUR");
        return labels;
    }


    public void getData(){
        HttpsInterface http = new HttpsInterface(config.getString("currencyURL"));
        String strXml=http.sendRequest();
        errorText="Не могу получить данные";
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
                        errorText="";
                    } catch (NumberFormatException e) {
                        this.usd = (float) 0.0;
                        this.eur = (float) 0.0;

                    }
                }
            }

        }

    }



    public List<Float> getValues() {
        List<Float> values=new ArrayList<>();
        values.add(usd);
        values.add(eur);
        return values;
    }

    public List<String> getComboList(){
        return null;
    };

    public String getErrorText() {
        return errorText;
    }

    public int getError() {
        return error;
    }
}

