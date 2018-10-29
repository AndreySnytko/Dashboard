package Dashboard;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;

public class Utils {

    private static String errorText;
    private static int error=0;

    final static Logger logger = Logger.getLogger(Utils.class);


    public static Configuration config() {

        try {
            Configuration config = new PropertiesConfiguration("./dashboard.config");
            return config;
        } catch (Exception e) {
            errorText="Конфиг не найден, использую значения по-умолчанию";
            Configuration config = new PropertiesConfiguration();
            config.setProperty("host", "127.0.0.1");
            config.setProperty("port", "27017");
            config.setProperty("dbname", "dashboard");
            //config.setProperty("login", "root");
            //config.setProperty("password", "root");
            config.setProperty("table", "visitors");
            config.setProperty("weatherURL", "http://api.apixu.com/v1/forecast.xml?key=dec6a405f5914a8bbe070116181110&days=2&q="); //q=Novosibirsk
            config.setProperty("currencyURL", "https://www.cbr.ru/scripts/XML_daily.asp");

            logger.error(errorText);
            logger.debug(errorText,e);

            return config;
        }

    }

}