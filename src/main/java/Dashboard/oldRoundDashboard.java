package Dashboard;

import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;


public class oldRoundDashboard extends VerticalLayout {

    private Info object;//Погода, валюта, счетчик

    public oldRoundDashboard(Info object){
        this.object=object;
    }

    public VerticalLayout drawWindow(){  //Отрисовываем окно

//        Window subWindow=new Window(object.getName());
        VerticalLayout verticalLayout = new VerticalLayout();


        /*TOT2018*/
//        subWindow.addStyleName("backColorBrown");
//        subWindow.addStyleName("rounded_border");

        //verticalLayout.addStyleName("rounded_border");
//        subWindow.addStyleName("rounded_border");
//        subWindow.addStyleName("backColorBlack");

        CustomLayout customLayout = new CustomLayout("roundlayout");
        customLayout.addComponent(new Label("RoundLabel"), "redloc");
//        customLayout.addStyleName("rounded_border2");
//        customLayout.addStyleName("backColorBlack");
        verticalLayout.addStyleName("circle");
        verticalLayout.addStyleName("backColorGrey");
        verticalLayout.addComponents(customLayout);





        //TOT2018 verticalLayout.setHeight("100%");


//        subWindow.setContent(verticalLayout);
//        subWindow.setHeight("300px");
//        subWindow.setWidth("300px");
//        subWindow.setClosable(false);
//        subWindow.setResizable(false);

        return verticalLayout;
    }


}
