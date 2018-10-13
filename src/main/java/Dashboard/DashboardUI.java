package Dashboard;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.ContentMode;
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

        final Window window = new Window();
        window.center();
        window.setWidth("1000px");
        window.setHeight("500px");

//        Label mainLabel=new Label("<H3>Тестовое сетевое приложение</H3>",ContentMode.HTML);
        Label mainLabel=new Label("Тестовое сетевое приложение");
//        final VerticalLayout verticalLayout=new VerticalLayout(); //Главное окно в которое будем помещать наши дашборды
//        verticalLayout.addComponent(mainLabel);
        setContent(mainLabel);


        //TODO: Менять расположение и размер окон в зависимости от разрешения браузера
        int browserWeidth=UI.getCurrent().getPage().getBrowserWindowWidth();
        int browserHeight=UI.getCurrent().getPage().getBrowserWindowHeight();
        int x=0;
        int y=0;
        System.out.println("Width: "+UI.getCurrent().getPage().getBrowserWindowWidth());
        System.out.println("Height: "+UI.getCurrent().getPage().getBrowserWindowHeight());


        //Если ширина браузера меньше 280 то размещаем вертикально, если отношение сторон высота/ширину>1.5
        if (Integer.valueOf(browserHeight)>Integer.valueOf(browserWeidth*3/2)){
            x=310;
        }



        // Создаём окно ожидания
//        Wait wait=new Wait();
//        SquareDashboard waitDashboard=new SquareDashboard(wait);
//        Window waitWindow = waitDashboard.drawWindow();
//        waitWindow.setPosition(10,10);
//        addWindow(waitWindow);

        // Создаём окно погоды
        //TODO:Можно сохранить в localStorage какую погоду просматривал пользователь в последний раз и передвать этот город в конструктор.
        Weather weather=new Weather();
        SquareDashboard weatherDashboard=new SquareDashboard(weather);
        Window weatherWindow = weatherDashboard.drawWindow();
        //        Window weatherWindow = (new SquareDashboard(new Weather())).drawWindow();
        //Размещаем панель погоды в главном окне
        weatherWindow.setPosition(10,40); //TODO: Отслеживать позицию других окон и размещать новое окно относительно уже добавленных
        addWindow(weatherWindow);



//        horizontalLayout.addComponent(weatherWindow);


        // Создаём окно курса валюты
        Currency currency=new Currency();
        SquareDashboard currencyDashboard=new SquareDashboard(currency);
        Window currencyWindow = currencyDashboard.drawWindow();
        //Размещаем панель курса валют в главном окне
        currencyWindow.setPosition(320-x,40+x);
        addWindow(currencyWindow);
//        horizontalLayout.addComponent(currencyWindow);


        // Создаём окно посещений
        Visitors visitors=new Visitors(properties);
        SquareDashboard visitorsDashboard=new SquareDashboard(visitors);
        Window visitorsWindow = visitorsDashboard.drawWindow();
        //Размещаем панель посещений в главном окне
        visitorsWindow.setPosition(630-x*2,40+x*2);
        addWindow(visitorsWindow);




//        horizontalLayout.addComponent(visitorsWindow);
//        horizontalLayout.addComponent(new Label("Thanks "));
//        setContent(horizontalLayout);
//        Window window=new Window("Main");



    }

    @WebServlet(urlPatterns = "/*", name = "DashboardServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = DashboardUI.class, productionMode = false)
    public static class DashboardServlet extends VaadinServlet {
    }

    public Properties config(){
        //TODO: добавить чтение конфига из файла
        Properties prop = new Properties();
        prop.setProperty("host", "greenmon.ru");
        prop.setProperty("port", "27017");
        prop.setProperty("dbname", "admin");
        prop.setProperty("login", "root");
        prop.setProperty("password", "root");
        prop.setProperty("table", "visitors");
        //TODO: Добавить чтение URLов для погоды и валюты из файла.

        return prop;
    }
}
