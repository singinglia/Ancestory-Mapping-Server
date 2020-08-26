package jsonModels;

import model.Event;

import java.util.List;

public class EventList {
    List<Event> data;

    public EventList(List<Event> data) {
        this.data = data;
    }

    public void setData(List<Event> data) {
        this.data = data;
    }

    public List<Event> getData() {
        return data;
    }
}
