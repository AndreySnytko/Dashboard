package Dashboard;

//import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;

import java.text.DecimalFormat;


public class SquareDashboard extends Window {

    private Info object;//Погода, валюта, счетчик

    public SquareDashboard(Info object){
        this.object=object;
    }

    public Window drawWindow(){  //Отрисовываем окно

        Window subWindow=new Window(object.getName());
        VerticalLayout verticalLayout = new VerticalLayout();

        Button button = new Button("Обновить");
        Label label1=new Label(); //label1.addStyleName("label"); TODO: Разобраться со стилями
        Label label2=new Label();


        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        //Прорисовываем окна в зависимости от того какой объект получили
        if(object instanceof Weather) {
            ComboBox boxCities = new ComboBox("", object.getComboList());
            boxCities.setValue(object.getComboList().get(0)); //TODO: убрать возможность выбора null

            //В случае нажатия кнопки обновляем данные и меняем значение лейблов
            button.addClickListener(e -> {
                String city=boxCities.getValue().toString();
                verticalLayout.addComponent(new Label(city));
                ((Weather) object).getData();
            });

            boxCities.setSizeFull();
            verticalLayout.addComponents(boxCities);


            label1.setCaption("Температура текущая:"); //TODO: Вынести названия в класс Weather
            label2.setCaption("Прогноз на завтра:");

            label1.setValue(decimalFormat.format(object.getValues().get(0)));
            label2.setValue(decimalFormat.format(object.getValues().get(1)));

            //label.setValue(object.getValues().get(1).toString());

            verticalLayout.addComponents(label1);
            verticalLayout.addComponents(label2);
            verticalLayout.addComponents(button);

        }else if(object instanceof Currency){


            label1.setCaption("USD:"); //TODO: Вынести названия в класс Currency
            label2.setCaption("EUR:");
            label1.setValue(decimalFormat.format(object.getValues().get(0)));
            label2.setValue(decimalFormat.format(object.getValues().get(1)));

            verticalLayout.addComponents(label1);
            verticalLayout.addComponents(label2);
            verticalLayout.addComponents(button);
        }else{

            label1.setValue(decimalFormat.format(object.getValues().get(0)));
            verticalLayout.addComponents(label1);
        }

        subWindow.setContent(verticalLayout);
        subWindow.setHeight("250px");
        subWindow.setWidth("250px");
        subWindow.setClosable(false);
        subWindow.setDraggable(false);
        subWindow.setResizable(false);

        return subWindow;
    }


}
