package Dashboard;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;

public class Utils {

    public static Configuration config() {

        try {
            Configuration config = new PropertiesConfiguration("dashboard.txt");
            return config;
        } catch (Exception e) {
            System.out.println("Конфиг не найден, использую значения по-умолчанию");
            Configuration config = new PropertiesConfiguration();
            config.setProperty("host", "greenmon.ru");
            config.setProperty("port", "27017");
            config.setProperty("dbname", "admin");
            config.setProperty("login", "root");
            config.setProperty("password", "root");
            config.setProperty("table", "visitors");
            config.setProperty("weatherURL", "http://api.apixu.com/v1/forecast.xml?key=dec6a405f5914a8bbe070116181110&days=2&q="); //q=Novosibirsk
            config.setProperty("currencyURL", "https://www.cbr.ru/scripts/XML_daily.asp");
            return config;
        }

    }

}