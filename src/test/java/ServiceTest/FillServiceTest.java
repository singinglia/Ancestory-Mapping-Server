package ServiceTest;

import Dao.DataAccessException;
import Dao.Database;
import Dao.UserDAO;
import model.Event;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.FillService;
import requests.FillRequest;
import results.FillResult;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

public class FillServiceTest {

    Database db;
    User bestUser;

    @BeforeEach
    public void setUp() throws Exception {

        db = new Database();
        bestUser = new User("flames", "123456", "fire@skyhigh.com", "Warren",
                "Peace", "m", "sky1543829");


        try {
            Connection conn = db.openConnection();
            UserDAO userDAO = new UserDAO(conn);

            if(userDAO.find(bestUser.getUsername()) == null) {
                userDAO.insertUser(bestUser);
            }
            db.closeConnection(true);
        }catch (DataAccessException e) {
            db.closeConnection(false);
        }


    }


    @Test
    public void FillServicePass(){
        boolean didItWork = true;

        try {
            FillRequest request = new FillRequest("flames");
            FillService fService = new FillService();
            fService.fill(request);

        } catch (IOException | DataAccessException e) {
            didItWork = false;
            e.printStackTrace();
        }

        assertTrue(didItWork);

    }

    @Test
    public void EventGenerationTest(){
        boolean mCorrectDeathAge = false;
        boolean fCorrectDeathAge = false;
        boolean mBornBeforeChild = true;
        boolean fBornBeforeChild = true;
        //boolean f13Older = false;
        //boolean mother13Older = false;
        try {
            FillRequest request = new FillRequest("flames");
            FillService fService = new FillService();
            fService.generateCoupleEvents("WOWZA", "PAPA", 1997);
            ArrayList<Event> events= fService.getAncestorEvents();

            // 0 Mother Birth 1 Father Birth 2 Mother Marriage 3 Father M 4 Mother Death 5 Father Death
            if(events.get(0).getYear() + 13 <= 1997){

                mBornBeforeChild = false;
            }
            else{
                System.out.println(1997 - events.get(0).getYear());
                mBornBeforeChild= true;
            }

            if(events.get(1).getYear() + 13 <= 1997){
                fBornBeforeChild = false;
            }
            else{
                System.out.println(1997 - events.get(1).getYear());
                fBornBeforeChild= true;
            }


            if(events.get(4).getYear() - events.get(0).getYear() <= 120){

                mCorrectDeathAge = true;
            }
            else{
                System.out.println(events.get(4).getYear() - events.get(0).getYear());
            }

            if(events.get(4).getYear() - events.get(0).getYear() <= 120){
                fCorrectDeathAge = true;
            } else{
                System.out.println(events.get(4).getYear() - events.get(0).getYear());
            }



        } catch (IOException e) {
            e.printStackTrace();
        }

        assertFalse(fBornBeforeChild);
        assertFalse(mBornBeforeChild);

        assertTrue(mCorrectDeathAge);
        assertTrue(fCorrectDeathAge);

    }

    @Test
    public void PersonCreate(){
        boolean correctNumPersons = false;
        try {
            FillRequest request = new FillRequest("flames");
            FillService fService = new FillService();
            FillResult result = fService.fill(request);
            String message = result.getResultMessage();
            if (message.equals( "Successfully added 31 persons and 91 events to the database.")){
                correctNumPersons = true;
            } else{
                System.out.println(message);
            }

        } catch (IOException | DataAccessException e) {
            e.printStackTrace();
        }

        assertTrue(correctNumPersons);
    }



}
