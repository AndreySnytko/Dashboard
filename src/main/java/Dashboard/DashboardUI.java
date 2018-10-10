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

        //Квадратный дашбоэрд
        final VerticalLayout verticalLayout = new VerticalLayout();
        final TextField name = new TextField();
        name.setCaption("Type your name here:");
        Button button = new Button("Click Me");
        button.addClickListener(e -> {
            verticalLayout.addComponent(new Label("Thanks " + name.getValue()
                    + ", it works!"));
        });
        verticalLayout.addComponents(name, button);


        // Рисуем панель погоды
        Weather weather=new Weather();
        SquareDashboard weatherDashboard=new SquareDashboard(weather);
        final VerticalLayout weatherLayout = weatherDashboard.drawWindow();

        //Рисуем окно в которое помещаем погоду
        Window subWindow=new Window("Погода");
        subWindow.setContent(weatherLayout);
        subWindow.setHeight("250px");
        subWindow.setWidth("250px");
        subWindow.setPosition(10,10);
        subWindow.setClosable(false);
        addWindow(subWindow);

        //Тестовое окно
        Window subWindow2=new Window("Vertical");
        subWindow2.setContent(verticalLayout);
        subWindow2.setHeight("250px");
        subWindow2.setWidth("250px");
        subWindow2.setPosition(270,10);
        subWindow2.setClosable(false);
        addWindow(subWindow2);


        // Собираем панели в единый дашбоэрд
//        horizontalLayout.addComponent(subWindow);
//        horizontalLayout.addComponent(weatherLayout);
//        horizontalLayout.addComponent(subWindow);

//        setContent(horizontalLayout);
    }

    @WebServlet(urlPatterns = "/*", name = "DashboardServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = DashboardUI.class, productionMode = false)
    public static class DashboardServlet extends VaadinServlet {
    }
}
