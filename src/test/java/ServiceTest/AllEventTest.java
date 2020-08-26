package ServiceTest;

import Dao.*;
import model.Event;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.AllEventService;
import requests.AllEventRequest;
import requests.AllPersonRequest;
import results.AllEventResult;
import results.AllPersonResult;

import java.sql.Connection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AllEventTest {

    Database db;
    Event eventWrongUsername;
    Event event1;
    Event event2;
    Event event3;
    User bestUser;
    AllEventRequest requestCorrect;

    @BeforeEach
    public void setUp() throws Exception {
        db = new Database();
        eventWrongUsername = new Event("123456", "gottarock", "p5456", 56,
                24, "USA", "SLC", "Game", 2004);
        event1 = new Event("coolness456", "burningLove", "p54321", 56,
                        24, "USA", "SLC", "Game", 2004);
        event2 = new Event("coolness123", "burningLove", "p54321", 23,
                29, "USA", "SLC", "Birth", 1980);
        event3 = new Event("coolness789", "burningLove", "p54321", 56,
                24, "Japan", "Toyko", "Tennis Match", 2009);
        bestUser = new User("burningLove", "123456", "fire@skyhigh.com", "Warren",
                "Peace", "m", "sky1543829");

        requestCorrect = new AllEventRequest("burningLove");

        try {
            Connection conn = db.openConnection();

            UserDAO userDAO = new UserDAO(conn);
            EventDAO eDao = new EventDAO(conn);

            db.clearTables();

            userDAO.insertUser(bestUser);
            eDao.insert(event1);
            eDao.insert(event2);
            eDao.insert(event3);
            eDao.insert(eventWrongUsername);

            db.closeConnection(true);
        }catch (DataAccessException e) {
            db.closeConnection(false);
        }

    }


    @Test
    public void getEventPass(){

        AllEventService eService = new AllEventService();
        AllEventResult result = eService.getPeople(requestCorrect);

        List<Event> events = result.getEvents();

        boolean found3Event = false;
        if (events.size() == 3){
            found3Event = true;
        }
        else{
            System.out.println(events.size());
        }
        assertTrue(found3Event);

        boolean notNull = true;
        if (events.get(0) == null){
            notNull = false;
        }
        assertTrue(notNull);
    }

    @Test
    void getFail(){
        AllEventService pService = new AllEventService();
        //Auth Checked above and username generated from auth
        //Null request
        AllEventRequest nRequest = null;
        AllEventResult result2 = pService.getPeople(nRequest);

        assertFalse(result2.getSuccess());

    }
}
