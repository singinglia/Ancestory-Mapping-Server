package test.daotest;

import Dao.*;
import model.AuthToken;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AuthDAOTest {
    private Database db;
    private AuthToken myAuth;
    private AuthToken myAuth2;
    private User bestUser;

    @BeforeEach
    public void setUp() throws Exception {
        //here we can set up any classes or variables we will need for the rest of our tests
        //lets create a new database
        db = new Database();
        //and a new event with random data
        myAuth = new AuthToken("sing4love", "sbaopeigb", "sdkl236939");
        myAuth2 = new AuthToken("sing4love", "sbaopeigb", "WowYoureBeautiful");
        bestUser = new User("sing4love", "123456", "fire@skyhigh.com", "Warren",
                "Peace", "m", "sky1543829");
    }


    @AfterEach
    public void tearDown() throws Exception {
        //here we can get rid of anything from our tests we don't want to affect the rest of our program
        //lets clear the tables so that any data we entered for testing doesn't linger in our files
        db.openConnection();
        db.clearTables();
        db.closeConnection(true);
    }

    @Test
    public void insertPass() throws Exception {
        //We want to make sure insert works
        //First lets create an Event that we'll set to null. We'll use this to make sure what we put
        //in the database is actually there.
        AuthToken compareTest = null;
        Boolean isSameUserName = null;
        AuthToken twoTokensTest1 = null;
        AuthToken twoTokensTest2 = null;

        try {
            //Let's get our connection and make a new DAO
            Connection conn = db.openConnection();
            AuthDAO eDao = new AuthDAO(conn);
            UserDAO userDAO = new UserDAO(conn);

            userDAO.insertUser(bestUser);
            eDao.insert(myAuth);
            //So lets use a find method to get the event that we just put in back out
            compareTest = eDao.find(myAuth.getAuth_token());

            isSameUserName = compareTest.getUsername().equals(myAuth.getUsername());


            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        //First lets see if our find found anything at all. If it did then we know that if nothing
        //else something was put into our database, since we cleared it in the beginning
        assertNotNull(compareTest);
        //Now lets make sure that what we put in is exactly the same as what we got out. If this
        //passes then we know that our insert did put something in, and that it didn't change the
        //data in any way
        assertEquals(myAuth, compareTest);

        assertTrue(isSameUserName);

        //Two different Tokens can exist
        try {
            //Let's get our connection and make a new DAO
            Connection conn = db.openConnection();
            AuthDAO eDao = new AuthDAO(conn);
            UserDAO userDAO = new UserDAO(conn);

            eDao.insert(myAuth2);
            //So lets use a find method to get the event that we just put in back out
            twoTokensTest1 = eDao.find(myAuth.getAuth_token());
            twoTokensTest2 = eDao.find(myAuth2.getAuth_token());



            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        //First lets see if our find found anything at all. If it did then we know that if nothing
        //else something was put into our database, since we cleared it in the beginning
        assertNotNull(twoTokensTest1);
        assertNotNull(twoTokensTest2);
    }

    @Test
    public void insertFail() throws Exception {
        //lets do this test again but this time lets try to make it fail

        // NOTE: The correct way to test for an exception in Junit 5 is to use an assertThrows
        // with a lambda function. However, lambda functions are beyond the scope of this class
        // so we are doing it another way.
        boolean didItWork = true;
        try {
            Connection conn = db.openConnection();
            AuthDAO eDao = new AuthDAO(conn);
            UserDAO userDAO = new UserDAO(conn);

            userDAO.insertUser(bestUser);
            //if we call the method the first time it will insert it successfully

            eDao.insert(myAuth);
            //but our sql table is set up so that "eventID" must be unique. So trying to insert it
            //again will cause the method to throw an exception
            eDao.insert(myAuth);
            db.closeConnection(true);
        } catch (DataAccessException e) {
            //If we catch an exception we will end up in here, where we can change our boolean to
            //false to show that our function failed to perform correctly
            db.closeConnection(false);
            didItWork = false;
        }
        //Check to make sure that we did in fact enter our catch statement
        assertFalse(didItWork);

        //Since we know our database encountered an error, both instances of insert should have been
        //rolled back. So for added security lets make one more quick check using our find function
        //to make sure that our event is not in the database
        //Set our compareTest to an actual event
        AuthToken compareTest = myAuth;
        try {
            Connection conn = db.openConnection();
            AuthDAO eDao = new AuthDAO(conn);
            //and then get something back from our find. If the event is not in the database we
            //should have just changed our compareTest to a null object
            compareTest = eDao.find(myAuth.getAuth_token());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }

        //Now make sure that compareTest is indeed null
        assertNull(compareTest);
    }
}
