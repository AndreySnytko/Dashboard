package Dashboard;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

//Получаем данные с сайта погоды
public class ParseURL {

    //String url="https://www.gismeteo.ru/weather-novosibirsk-4690/now/";

    public ParseURL(String url) {
        try
        {
            Document doc = Jsoup.connect(url).get();
            Elements elem = doc.select("span.js_value.tab-weather__value_l"); //погода сегодня
            System.out.println(url+"\n"+elem);
        }catch(IOException e)
        {

        }
    }




}
