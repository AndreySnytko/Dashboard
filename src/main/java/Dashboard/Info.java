package Dashboard;


import java.util.List;

public interface Info {
    List<String> getComboList();
    List<Float> getValues();
    List<String> getLabels();
    String getName();
}
