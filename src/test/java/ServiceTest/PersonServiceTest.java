package ServiceTest;

import Dao.*;
import model.AuthToken;
import model.Person;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.PersonService;
import requests.EventRequest;
import requests.PersonRequest;
import results.EventResult;
import results.PersonResult;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class PersonServiceTest {
    private Database db;
    private Person person1;
    private Person person2;
    private User bestUser;
    private AuthToken authToken;

    @BeforeEach
    public void setUp() throws Exception {


        db = new Database();
        person1 = new Person("p13333", "DollyParton", "Jolene",
                "Stealer", "f");
        person2 = new Person("p12345", "burningLove", "Eileen",
                "Gale", "f");
        bestUser = new User("burningLove", "123456", "fire@skyhigh.com", "Warren",
                "Peace", "m", "sky1543829");
        authToken = new AuthToken("1235235253w", "DollyParton");

        try {
            Connection conn = db.openConnection();

            AuthDAO eDao = new AuthDAO(conn);
            UserDAO userDAO = new UserDAO(conn);
            PersonDAO personDao = new PersonDAO(conn);
            AuthDAO aDao = new AuthDAO(conn);

            db.clearTables();

            userDAO.insertUser(bestUser);
            personDao.insert(person1);
            personDao.insert(person2);
            aDao.insert(authToken);

            db.closeConnection(true);
        }catch (DataAccessException e) {
            db.closeConnection(false);
        }


    }

    @Test
    public void insertPass() throws Exception {

        boolean isEqual = false;

        PersonService personService = new PersonService();
        PersonRequest request = new PersonRequest("p13333", authToken.getAuth_token());
        PersonResult result = personService.getPerson(request);

        isEqual = result.getAssociatedUsername().equals(person1.getAssociatedUsername());
        assertTrue(isEqual);



    }

    @Test
    public void getEventFail(){
        PersonRequest requestWrong  = new PersonRequest("p13333", "NOT");


        PersonService pService = new PersonService();
        PersonResult result = pService.getPerson(requestWrong);

        assertFalse(result.getSuccess());

        PersonRequest eReq = null;

        PersonResult result2 = pService.getPerson(eReq);

        assertFalse(result2.getSuccess());

    }
}
