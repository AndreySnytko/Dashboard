package Dashboard;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;

import java.util.Properties;

import static java.security.AccessController.getContext;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class DashboardUI extends UI {

    Properties properties;

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        properties=config();//Вычитываем конфиг

        //final HorizontalLayout horizontalLayout=new HorizontalLayout(); //Главное окно в которое будем помещать наши дашборды

        //TODO: Менять расположение и размер окон в зависимости от разрешения браузера
        //System.out.println("Width: "+UI.getCurrent().getPage().getBrowserWindowWidth());
        //System.out.println("Height: "+UI.getCurrent().getPage().getBrowserWindowHeight());

        // Создаём окно погоды
        //TODO:Можно сохранить в localStorage какую погоду просматривал пользователь в последний раз и передвать этот город в конструктор.
        Window weatherWindow = (new SquareDashboard(new Weather())).drawWindow();
        //Размещаем панель погоды в главном окне
        weatherWindow.setPosition(10,10); //TODO: Отслеживать позицию других окон и размещать новое окно относительно уже добавленных
        addWindow(weatherWindow);

        // Создаём окно курса валюты
        Currency currency=new Currency();
        SquareDashboard currencyDashboard=new SquareDashboard(currency);
        Window currencyWindow = currencyDashboard.drawWindow();
        //Размещаем панель курса валют в главном окне
        currencyWindow.setPosition(270,10);
        addWindow(currencyWindow);

        // Создаём окно посещений
        Visitors visitors=new Visitors(properties);
        SquareDashboard visitorsDashboard=new SquareDashboard(visitors);
        Window visitorsWindow = visitorsDashboard.drawWindow();
        //Размещаем панель посещений в главном окне
        visitorsWindow.setPosition(530,10);
        addWindow(visitorsWindow);
    }

    @WebServlet(urlPatterns = "/*", name = "DashboardServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = DashboardUI.class, productionMode = false)
    public static class DashboardServlet extends VaadinServlet {
    }

    public Properties config(){
        //TODO: добавить чтение конфига из файла
        Properties prop = new Properties();
        prop.setProperty("host", "localhost");
        prop.setProperty("port", "27017");
        prop.setProperty("dbname", "admin");
        prop.setProperty("login", "root");
        prop.setProperty("password", "root");
        prop.setProperty("table", "visitors");
        //TODO: Добавить чтение URLов для погоды и валюты из файла.

        return prop;
    }
}
