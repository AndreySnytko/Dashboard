package Dashboard;

import com.vaadin.ui.*;
import org.apache.log4j.Layout;

import java.text.DecimalFormat;


public class RoundDashboard extends VerticalLayout {

    private Info object;//Погода, валюта, счетчик

    public RoundDashboard(Info object){
        this.object=object;
    }

    public VerticalLayout drawWindow(){  //Отрисовываем окно
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.addStyleName("circle");
//        verticalLayout.addStyleName("backColorGrey ");




        verticalLayout.setMargin(true);// Добавим внешние отступы лейауту
        verticalLayout.setSpacing(true);// Добавим отступы между компонентами лейаута


        Button button = new Button("Обновить"); button.setWidthUndefined();
        Label label1=new Label();
        Label label2=new Label();
        TextField textField1 = new TextField(); textField1.setReadOnly(true); textField1.setWidth("60");//TODO:высчитывать размер от количества символов (em?)
        TextField textField2 = new TextField(); textField2.setReadOnly(true); textField2.setWidth("60");
        textField1.setStyleName("smalltext");
        textField2.setStyleName("smalltext");

        //Layout для подписей и значений
        HorizontalLayout horizontalLayout1 = new HorizontalLayout(); /*horizontalLayout1.addStyleName("backColorGrey");*/ horizontalLayout1.setWidth("100%");
        HorizontalLayout horizontalLayout2 = new HorizontalLayout(); /*horizontalLayout2.addStyleName("backColorBrown");*/ horizontalLayout2.setWidth("100%");
//        horizontalLayout1.setDefaultComponentAlignment(Alignment.BOTTOM_CENTER);
        horizontalLayout1.addComponent(label1); horizontalLayout1.setComponentAlignment(label1,Alignment.MIDDLE_LEFT); label1.setStyleName("description");
        horizontalLayout1.addComponent(textField1);  horizontalLayout1.setComponentAlignment(textField1,Alignment.MIDDLE_RIGHT);
//        horizontalLayout2.setDefaultComponentAlignment(Alignment.BOTTOM_CENTER);
        horizontalLayout2.addComponent(label2); horizontalLayout2.setComponentAlignment(label2,Alignment.MIDDLE_LEFT);label2.setStyleName("description");
        horizontalLayout2.addComponent(textField2);  horizontalLayout2.setComponentAlignment(textField2,Alignment.MIDDLE_RIGHT);


        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        //Прорисовываем окна в зависимости от того какой объект получили
        if(object instanceof Weather) { //Погода

            ComboBox boxCities = new ComboBox("", object.getComboList());
            boxCities.setValue(object.getComboList().get(0));
            boxCities.setEmptySelectionAllowed(false);
            boxCities.addStyleName("combobox");

            //В случае нажатия кнопки обновляем данные и меняем значение лейблов
            button.addClickListener(e -> {
                String city=boxCities.getValue().toString();
                ((Weather) object).getData(city);
                textField1.setValue(decimalFormat.format(object.getValues().get(0))+"C");
                textField2.setValue(decimalFormat.format(object.getValues().get(1))+"C");
            });

            //В случае изменения значения comboBox
            boxCities.addValueChangeListener(event -> {
                String city=boxCities.getValue().toString();
                ((Weather) object).getData(city);
                textField1.setValue(decimalFormat.format(object.getValues().get(0))+"C");
                textField2.setValue(decimalFormat.format(object.getValues().get(1))+"C");
            });



            //Выпадающий список
            boxCities.setSizeFull();
            verticalLayout.addComponents(boxCities);

            //Значения с подписями
            if(((Weather) object).getError()==0){

                label1.setValue("Температура текущая:"); //TODO: Вынести названия в класс Weather
                label2.setValue("Прогноз на завтра:");

                textField1.setValue(decimalFormat.format(object.getValues().get(0))+"C");
                textField2.setValue(decimalFormat.format(object.getValues().get(1))+"C");

                verticalLayout.addComponents(horizontalLayout1);
                verticalLayout.addComponents(horizontalLayout2);

            }else{
                label1.setValue("Не могу получить данные");
                verticalLayout.addComponents(label1);
                verticalLayout.setComponentAlignment(label1, Alignment.MIDDLE_CENTER);
            }

            //Кнопка
            verticalLayout.addComponents(button); verticalLayout.setComponentAlignment(button, Alignment.BOTTOM_CENTER);

        }else if(object instanceof Currency){ //Курс валют

            button.addClickListener(e -> {
                ((Currency) object).getData();
                textField1.setValue(decimalFormat.format(object.getValues().get(0)));
                textField2.setValue(decimalFormat.format(object.getValues().get(1)));
            });


            if(((Currency) object).getError()==0) {
                label1.setValue("USD:"); //TODO: Вынести названия в класс Currency
                label2.setValue("EUR:");
                textField1.setValue(decimalFormat.format(object.getValues().get(0)));
                textField2.setValue(decimalFormat.format(object.getValues().get(1)));
                //Значения с подписями
                //TODO: Вынести игры с расположением в style.css
                horizontalLayout1.setComponentAlignment(label1, Alignment.MIDDLE_RIGHT);
                horizontalLayout1.setComponentAlignment(textField1, Alignment.MIDDLE_LEFT);
                horizontalLayout2.setComponentAlignment(label2, Alignment.MIDDLE_RIGHT);
                horizontalLayout2.setComponentAlignment(textField2, Alignment.MIDDLE_LEFT);
                horizontalLayout1.setMargin(false);horizontalLayout1.setSpacing(false);
                horizontalLayout2.setMargin(false);horizontalLayout2.setSpacing(false);
                verticalLayout.setMargin(true);verticalLayout.setSpacing(false);
                verticalLayout.addComponents(horizontalLayout1);verticalLayout.setComponentAlignment(horizontalLayout1, Alignment.BOTTOM_CENTER);
                verticalLayout.addComponents(horizontalLayout2);verticalLayout.setComponentAlignment(horizontalLayout2, Alignment.BOTTOM_CENTER);
            }else{
                label1.setValue(((Currency) object).getErrorText());
                verticalLayout.addComponents(label1);
                verticalLayout.setComponentAlignment(label1, Alignment.MIDDLE_CENTER);
            }

            //Кнопка
            verticalLayout.addComponents(button);verticalLayout.setComponentAlignment(button, Alignment.BOTTOM_CENTER);

        }else if(object instanceof Visitors){ // Счетчик посещений
            float count=object.getValues().get(0);
            if(count!=0) {

                Label label =new Label(decimalFormat.format(count));
                label.setSizeUndefined(); label.setStyleName("bigtext");
                label.addStyleName("smallcircle");
                verticalLayout.addComponents(label);
                verticalLayout.setComponentAlignment(label, Alignment.BOTTOM_CENTER);
            }else{
                label1.setValue("Не могу получить данные");
                verticalLayout.addComponents(label1);
                verticalLayout.setComponentAlignment(label1, Alignment.MIDDLE_CENTER);
            }
        }else{
            //TODO: Ползунок загрузки
        }










        return verticalLayout;
    }


}
