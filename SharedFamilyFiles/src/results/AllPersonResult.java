package results;

import jsonModels.PersonList;
import model.Person;
import org.json.JSONObject;

import java.util.ArrayList;

public class AllPersonResult extends BasicResult {

    ArrayList<Person> data;

    public AllPersonResult(boolean wasSuccessful, String resultMessage) {
        super(wasSuccessful, resultMessage);
    }

    public AllPersonResult(boolean wasSuccessful, ArrayList<Person> personList) {
        super(wasSuccessful);
        this.data = personList;
    }

    public ArrayList<Person> getPersonList() {
        return data;
    }
}
