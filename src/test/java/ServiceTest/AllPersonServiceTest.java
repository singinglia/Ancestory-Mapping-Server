package ServiceTest;

import Dao.*;
import model.Person;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import services.AllPersonService;
import requests.AllPersonRequest;
import results.AllPersonResult;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.util.ArrayList;

public class AllPersonServiceTest {

    Database db;
    Person personWrongUsername;
    Person person1;
    Person person2;
    Person person3;
    User bestUser;
    AllPersonRequest requestCorrect;
    AllPersonRequest requestWrong;


    @BeforeEach
    public void setUp() throws Exception {
        db = new Database();
        personWrongUsername = new Person("p13333", "DollyParton", "Jolene",
                "Stealer", "f");
        person1 = new Person("p12345", "burningLove", "Sara",
                "Ramirez", "f");
        person2 = new Person("p189546", "burningLove", "Eileen",
                "Gale", "f");
        person3 = new Person("p54321", "burningLove", "David",
                "Gale", "m");
        bestUser = new User("burningLove", "123456", "fire@skyhigh.com", "Warren",
                "Peace", "m", "sky1543829");

        requestCorrect = new AllPersonRequest("burningLove");
        requestWrong = new AllPersonRequest("DollyParton");

        try {
            Connection conn = db.openConnection();

            AuthDAO eDao = new AuthDAO(conn);
            UserDAO userDAO = new UserDAO(conn);
            PersonDAO personDao = new PersonDAO(conn);

            db.clearTables();

            userDAO.insertUser(bestUser);
            personDao.insert(person1);
            personDao.insert(person2);
            personDao.insert(person3);
            personDao.insert(personWrongUsername);

            db.closeConnection(true);
        }catch (DataAccessException e) {
            db.closeConnection(false);
        }

    }


    @Test
    public void getPeoplePass(){

        AllPersonService pService = new AllPersonService();
        AllPersonResult result = pService.getPeople(requestCorrect);

        ArrayList<Person> people = result.getPersonList();

        boolean found3People = false;
        if (people.size() == 3){
            found3People = true;
        }
        else{
            System.out.println(people.size());
        }
        assertTrue(found3People);

        boolean notNull = true;
        if (people.get(0) == null){
            notNull = false;
        }
        assertTrue(notNull);
    }

    @Test
    void getFail(){
        AllPersonService pService = new AllPersonService();
        AllPersonResult result = pService.getPeople(requestWrong);

        AllPersonRequest nRequest = null;
        AllPersonResult result2 = pService.getPeople(nRequest);

        assertFalse(result2.getSuccess());

    }
}
