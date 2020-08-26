package requests;

import model.Event;
import model.Person;
import model.User;

import java.util.ArrayList;

public class LoadRequest {
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

    public LoadRequest(ArrayList<User> users, ArrayList<Person> persons, ArrayList<Event> events) {
        this.users = users;
        this.persons = persons;
        this.events = events;
    }
}
