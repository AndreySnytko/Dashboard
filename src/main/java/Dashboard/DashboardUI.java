package Dashboard;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class DashboardUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final HorizontalLayout horizontalLayout=new HorizontalLayout(); //Главное окно в которое будем помещать наши дашборды


        // Создаём окно погоды
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
        Visitors visitors=new Visitors();
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
}
