package ServiceTest;

import Dao.*;
import model.AuthToken;
import model.Event;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.EventService;
import requests.EventRequest;
import results.EventResult;

import java.sql.Connection;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EventServiceTest {
    Database db;
    Event eventWrongUsername;
    Event event1;
    Event event2;
    Event event3;
    User bestUser;
    EventRequest requestCorrect;
    EventRequest requestWrong;
    AuthToken authToken;

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
        authToken = new AuthToken("1235235253w", "burningLove");

        try {
            Connection conn = db.openConnection();

            UserDAO userDAO = new UserDAO(conn);
            EventDAO eDao = new EventDAO(conn);
            AuthDAO aDao = new AuthDAO(conn);

            db.clearTables();

            userDAO.insertUser(bestUser);
            eDao.insert(event1);
            eDao.insert(event2);
            eDao.insert(event3);
            eDao.insert(eventWrongUsername);
            aDao.insert(authToken);

            db.closeConnection(true);
        }catch (DataAccessException e) {
            db.closeConnection(false);
        }

        requestCorrect = new EventRequest("coolness456", authToken.getAuth_token());
        requestWrong = new EventRequest("coolness456", "authToken.getAuth_token()");

    }


    @Test
    public void getEventPass(){

        EventService eService = new EventService();
        EventResult result = eService.getEvent(requestCorrect);


        boolean foundEvent = false;
        if (result != null){
            foundEvent = true;
        }

        assertTrue(foundEvent);

        boolean correctInfo = false;
        if (result.getEventID().equals(event1.getEventID())){
            correctInfo = true;
        }
        assertTrue(correctInfo);
    }

    @Test
    public void getEventFail(){
        EventService eService = new EventService();
        EventResult result = eService.getEvent(requestWrong);

        assertFalse(result.getSuccess());

        EventRequest eReq = null;

        EventResult result2 = eService.getEvent(eReq);

        assertFalse(result2.getSuccess());

    }
}
