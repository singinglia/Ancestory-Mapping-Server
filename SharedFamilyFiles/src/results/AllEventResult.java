package results;
import model.Event;


import java.util.ArrayList;

public class AllEventResult extends BasicResult {

    ArrayList<Event> data;

    public AllEventResult(boolean wasSuccessful, ArrayList<Event> events) {
        super(wasSuccessful);
        this.data = events;
    }

    public AllEventResult(boolean wasSuccessful, String resultMessage) {
        super(wasSuccessful, resultMessage);
    }

    public ArrayList<Event> getEvents() {
        return data;
    }
}
