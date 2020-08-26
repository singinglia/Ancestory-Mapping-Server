package jsonModels;

import model.Event;
import model.Person;
import model.User;

import java.util.ArrayList;

public class LoadData {
    ArrayList<User> users;
    ArrayList<Person> persons;
    ArrayList<Event> events;

    public ArrayList<User> getLoadUsers() {
        return users;
    }

    public ArrayList<Person> getLoadPersons() {
        return persons;
    }

    public ArrayList<Event> getLoadEvents() {
        return events;
    }
}
