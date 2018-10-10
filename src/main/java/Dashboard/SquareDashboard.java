package Dashboard;

import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;

import java.util.ArrayList;

public class SquareDashboard extends VerticalLayout {

    private Info object;//Погода, валюта, счетчик

    public SquareDashboard(Info object){
        this.object=object;
    }

    public VerticalLayout drawWindow(){  //Отрисовываем окно

        VerticalLayout verticalLayout = new VerticalLayout();

        ArrayList<String> cities=new ArrayList<String>(); cities.add("Новосибирск"); cities.add("Москва");
        ComboBox boxCities=new ComboBox("",object.getComboList());
        boxCities.setSizeFull();
        verticalLayout.addComponents(boxCities);


        Button button = new Button("Обновить");
        button.addClickListener(e -> {
            verticalLayout.addComponent(new Label(boxCities.getValue().toString()));
        });


        verticalLayout.addComponents(button);

        return verticalLayout;
    }

    public void drawData(){

    }

    public void refresh(){//Обновляем данные на странице

    }
}
