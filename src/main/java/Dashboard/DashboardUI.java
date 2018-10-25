package Dashboard;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.WebBrowser;
import com.vaadin.ui.*;
import org.apache.commons.configuration.Configuration;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Theme("mytheme")
public class DashboardUI extends UI {


    Configuration config;
    final static Logger logger = Logger.getLogger(DashboardUI.class);

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        config=Utils.config();//Вычитываем конфиг

        Label mainLabel=new Label("Тестовое сетевое приложение");        mainLabel.addStyleName("greytext");

        //Получаем информацию о клиенте
        WebBrowser webBrowser = Page.getCurrent().getWebBrowser();
        String ip=webBrowser.getAddress();
        Label ipLabel=new Label("Ваш IP:"+ip); //TODO: Получить ещё и локальный ip пользователя; иногда возвращает IPv6(бага vaadin)
        logger.info("Пользвователь с IP адресом "+ip+" открыл страницу.");

        //Получаем параметры окна браузера и разрешение экрана
        int browserWeidth=UI.getCurrent().getPage().getBrowserWindowWidth();
        int browserHeight=UI.getCurrent().getPage().getBrowserWindowHeight();
        int screenWeidth=webBrowser.getScreenWidth();
        int screenHeight=webBrowser.getScreenHeight();

        String userDisplayResolution=screenWeidth+"x"+screenHeight+" ("+browserWeidth+"x"+browserHeight+")";
        Label displayLabel=new Label("Разрешение экрана: "+userDisplayResolution);

        final VerticalLayout verticalLayout=new VerticalLayout(); //Главное окно в которое будем помещать наши дашборды

        //Дата и время
        Date nowDate = new Date(); //Серверное время, можно получить TimeZone пользователя
        Label dateLabel=new Label("Информация по состояни на "+new SimpleDateFormat("dd.MM.YYYY HH:mm:ss",new Locale("ru")).format(nowDate));


        final HorizontalLayout buttonHorizontalLayout=new HorizontalLayout();
        buttonHorizontalLayout.addComponent(dateLabel); buttonHorizontalLayout.setComponentAlignment(dateLabel,Alignment.BOTTOM_LEFT);
        buttonHorizontalLayout.addComponent(ipLabel); buttonHorizontalLayout.setComponentAlignment(ipLabel,Alignment.BOTTOM_RIGHT);
        buttonHorizontalLayout.addStyleName("description");


        verticalLayout.setMargin(true);// Добавим внешние отступы лейауту
        verticalLayout.setSpacing(true);// Добавим отступы между компонентами лейаута
        verticalLayout.setWidth("940px");
        verticalLayout.setHeight(null);


        final Layout mainHorizontalLayout=new HorizontalLayout(); //mainHorizontalLayout.setStyleName("backColorBrown");
        mainLabel.setHeightUndefined();
        verticalLayout.addComponent(mainLabel);             verticalLayout.setComponentAlignment(mainLabel,Alignment.BOTTOM_CENTER);
        verticalLayout.addComponent(mainHorizontalLayout);  verticalLayout.setComponentAlignment(mainHorizontalLayout,Alignment.TOP_CENTER); mainHorizontalLayout.setSizeUndefined();
        verticalLayout.addComponent(buttonHorizontalLayout);verticalLayout.setComponentAlignment(buttonHorizontalLayout,Alignment.BOTTOM_CENTER); verticalLayout.setSizeUndefined();


        //Погода
        Weather weather=new Weather(config);
        RoundDashboard weatherDashboard=new RoundDashboard(weather);
        Layout weatherWindow = weatherDashboard.drawWindow();

        //Курс валют
        Currency currency=new Currency(config);
        RoundDashboard currencyDashboard=new RoundDashboard(currency);
        Layout currencyWindow = currencyDashboard.drawWindow();

        //Счётчик посещений
        Visitors visitors=new Visitors(config);
        RoundDashboard visitorsDashboard=new RoundDashboard(visitors);
        Layout visitorsWindow = visitorsDashboard.drawWindow();


        //Добавляем круглые окна на главное окно
        mainHorizontalLayout.addComponent(currencyWindow);
        mainHorizontalLayout.addComponent(visitorsWindow);
        mainHorizontalLayout.addComponent(weatherWindow);


        Window subWindow=new Window();
        subWindow.setHeight(null);
        subWindow.setStyleName("rounded");
        subWindow.setContent(verticalLayout);
        subWindow.setPosition(browserWeidth/2-940/2,browserHeight-580); //Позиционируем по центру внизу

        UI.getCurrent().setStyleName("main");
        UI.getCurrent().addWindow(subWindow);
        subWindow.setClosable(false);
        subWindow.setResizable(false);
    }

    @WebServlet(urlPatterns = "/*", name = "DashboardServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = DashboardUI.class, productionMode = false)
    public static class DashboardServlet extends VaadinServlet {
    }









}
