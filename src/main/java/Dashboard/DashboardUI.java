package Dashboard;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.WebBrowser;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;

@Theme("mytheme")
public class DashboardUI extends UI {

    Properties properties;

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        properties=config();//Вычитываем конфиг


        Label mainLabel=new Label("<H3>Тестовое сетевое приложение</H3>",ContentMode.HTML);

        //Получаем информацию о клиенте
        WebBrowser webBrowser = Page.getCurrent().getWebBrowser();
        Label ipLabel=new Label("Ваш IP:"+webBrowser.getAddress()); //TODO: Получить ещё и локальный ip пользователя; иногда возвращает IPv6(бага vaadin)

        //TODO: Менять расположение и размер окон в зависимости от разрешения дисплея и окна браузера

        //Получаем параметры окна браузера и разрешение экрана
        int browserWeidth=UI.getCurrent().getPage().getBrowserWindowWidth();
        int browserHeight=UI.getCurrent().getPage().getBrowserWindowHeight();
        int screenWeidth=webBrowser.getScreenWidth();
        int screenHeight=webBrowser.getScreenHeight();
        int x=0;
        int y=0;
        String userDisplayResolution=screenWeidth+"x"+screenHeight+" ("+browserWeidth+"x"+browserHeight+")";
        Label displayLabel=new Label(userDisplayResolution);

        final VerticalLayout verticalLayout=new VerticalLayout(); //Главное окно в которое будем помещать наши дашборды
        verticalLayout.addComponent(mainLabel); verticalLayout.setComponentAlignment(mainLabel,Alignment.TOP_CENTER);

        //Дата и время
        //Date nowDate = webBrowser.getCurrentDate(); //Локальное время пользователя
        Date nowDate = new Date(); //Серверное время, можно получить TimeZone пользователя
        Label dateLabel=new Label("Информация по состояни на "+new SimpleDateFormat("dd.MM.YYYY HH:mm:ss",new Locale("ru")).format(nowDate));

        final HorizontalLayout buttonHorizontalLayout=new HorizontalLayout();
        buttonHorizontalLayout.addComponent(dateLabel); buttonHorizontalLayout.setComponentAlignment(dateLabel,Alignment.BOTTOM_LEFT);
        buttonHorizontalLayout.addComponent(displayLabel); buttonHorizontalLayout.setComponentAlignment(displayLabel,Alignment.BOTTOM_CENTER);
        buttonHorizontalLayout.addComponent(ipLabel); buttonHorizontalLayout.setComponentAlignment(ipLabel,Alignment.BOTTOM_RIGHT);

        verticalLayout.addComponent(buttonHorizontalLayout);verticalLayout.setComponentAlignment(buttonHorizontalLayout,Alignment.BOTTOM_CENTER); verticalLayout.setSizeUndefined();
        verticalLayout.addStyleName("backColorBrown");
        verticalLayout.addStyleName("v-scrollable");


        verticalLayout.setMargin(false);// Добавим внешние отступы лейауту
        verticalLayout.setSpacing(false);// Добавим отступы между компонентами лейаута
        verticalLayout.setWidth("940px");
        verticalLayout.setHeight("380px");
        setContent(verticalLayout);

//        setContent(mainLabel);
//        verticalLayout.addComponent(mainLabel));
//        verticalLayout.addComponent(mainLabel));

        //Если ширина браузера меньше 280 то размещаем вертикально, если отношение сторон высота/ширину>1.5
        if (Integer.valueOf(browserHeight)>Integer.valueOf(browserWeidth*3/2)){ //TODO: Исправить для сафари
            x=310;
            verticalLayout.setWidth("320px");
            verticalLayout.setHeight("940px");

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
        weatherWindow.setPosition(10,50); //TODO: Отслеживать позицию других окон и размещать новое окно относительно уже добавленных
//        addWindow(weatherWindow);
        UI.getCurrent().addWindow(weatherWindow);



        // Создаём окно курса валюты
        Currency currency=new Currency();
        SquareDashboard currencyDashboard=new SquareDashboard(currency);
        Window currencyWindow = currencyDashboard.drawWindow();
        //Размещаем панель курса валют в главном окне
        currencyWindow.setPosition(320-x,50+x);
//        addWindow(currencyWindow);
        UI.getCurrent().addWindow(currencyWindow);

        // Создаём окно посещений
        Visitors visitors=new Visitors(properties);
        SquareDashboard visitorsDashboard=new SquareDashboard(visitors);
        Window visitorsWindow = visitorsDashboard.drawWindow();
        //Размещаем панель посещений в главном окне
        visitorsWindow.setPosition(630-x*2,50+x*2);
        UI.getCurrent().addWindow(visitorsWindow);



//        UI.getCurrent().addWindow(window);

//        UI.getCurrent().close(); - Делает все окна неактивными
//        UI.getCurrent().removeWindow(waitWindow); - Удаляем окна
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
