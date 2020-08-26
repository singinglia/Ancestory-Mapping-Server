package ServiceTest;

import Dao.DataAccessException;
import Dao.Database;
import Dao.UserDAO;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.ClearService;
import results.ClearResult;


import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class ClearServiceTest {


    private Database db;
    private User bestUser;

    @BeforeEach
    public void setUp() throws Exception {


        db = new Database();
        bestUser = new User("sing4love", "123456", "fire@skyhigh.com", "Warren",
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
    public void clearPass() throws Exception {

        ClearResult result = null;

        ClearService clearService = new ClearService();
        result = clearService.clear();

        assertNotNull(result);

        //Success
        assertTrue(result.getSuccess());


    }

}
