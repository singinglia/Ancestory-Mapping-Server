package ServiceTest;

import Dao.*;
import model.AuthToken;
import model.Event;
import model.Person;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import services.ClearService;
import services.LoadService;
import requests.EventRequest;
import requests.LoadRequest;
import results.EventResult;
import results.LoadResult;

import java.sql.Connection;
import java.util.ArrayList;

public class LoadServiceTest {
    Event event1;
    Event event2;
    Event event3;
    User bestUser;
    User user2;
    User user3;
    Person person1;
    Person person2;
    Person person3;
    ArrayList<Person> persons;
    ArrayList<Event> events;
    ArrayList<User> users;
    AuthToken authToken;
    Database db;

    LoadRequest loadRequest;

    @BeforeEach
    public void setup() throws DataAccessException {
        ClearService clear = new ClearService();
        clear.clear();

        events = new ArrayList<Event>();
        users = new ArrayList<User>();
        persons = new ArrayList<Person>();
        event1 = new Event("coolness456", "burningLove", "p54321", 56,
                24, "USA", "SLC", "Game", 2004);
        event2 = new Event("coolness123", "burningLove", "p54321", 23,
                29, "USA", "SLC", "Birth", 1980);
        event3 = new Event("coolness789", "burningLove", "p54321", 56,
                24, "Japan", "Toyko", "Tennis Match", 2009);
        bestUser = new User("burningLove", "123456", "fire@skyhigh.com", "Warren",
                "Peace", "m", "sky1543829");
        user2 = new User("dollyparton", "123456", "country@music.com", "Jolene",
                "Stealer", "f", "countrymuse");
        user3 = new User("gandolf", "123456", "fire@skyhigh.com", "gandolf",
                "gray", "m", "notpass");
        person1 = new Person("p12345", "burningLove", "Sara",
                "Ramirez", "f");
        person2 = new Person("p189546", "burningLove", "Eileen",
                "Gale", "f");
        person3 = new Person("p54321", "burningLove", "David",
                "Gale", "m");
        authToken = new AuthToken("1235235253w", "burningLove");

        events.add(event1);
        events.add(event2);
        events.add(event3);
        persons.add(person1);
        persons.add(person2);
        persons.add(person3);
        users.add(bestUser);
        users.add(user2);
        users.add(user3);

        loadRequest = new LoadRequest(users, persons, events);

        try {
            db = new Database();
            Connection conn = db.openConnection();

            UserDAO userDAO = new UserDAO(conn);
            EventDAO eDao = new EventDAO(conn);
            AuthDAO aDao = new AuthDAO(conn);

            aDao.insert(authToken);

            db.closeConnection(true);
        }catch (DataAccessException e) {
            db.closeConnection(false);
        }

    }

    @Test
    void LoadPass(){

        boolean didItWork = true;

        LoadService loadService = new LoadService();
        LoadResult result = loadService.load(loadRequest);

        if(result.getSuccess()){
            didItWork = true;
        }
        else{
            didItWork = false;
        }
        Event compareTest = null;
        try {
            db = new Database();
            Connection conn = db.openConnection();

            EventDAO eDao = new EventDAO(conn);

            compareTest = eDao.find(event1.getEventID());

            db.closeConnection(true);
        }catch (DataAccessException e) {
            try {
                db.closeConnection(false);
            } catch (DataAccessException ex) {
                ex.printStackTrace();
            }
        }

        assertNotNull(compareTest);

        assertTrue(didItWork);
    }



}
