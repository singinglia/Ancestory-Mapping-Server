package jsonModels;

import java.util.List;

public class NameList {
    List<String> data;

    public NameList(List<String> names) {
        this.data = names;
    }

    public List<String> getNames() {
        return data;
    }
}
