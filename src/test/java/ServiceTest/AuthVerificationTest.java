package ServiceTest;

import Dao.AuthDAO;
import Dao.DataAccessException;
import Dao.Database;
import Dao.UserDAO;
import model.AuthToken;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.AuthVerificationService;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AuthVerificationTest {

    private Database db;
    private AuthToken myAuth;
    private AuthToken myAuth2;
    private User bestUser;

    @BeforeEach
    public void setUp() throws Exception {


        db = new Database();
        myAuth = new AuthToken("sing4love", "sbaopeigb", "sdkl236939");
        myAuth2 = new AuthToken("sing4love", "sbaopeigb", "WowYoureBeautiful");
        bestUser = new User("sing4love", "123456", "fire@skyhigh.com", "Warren",
                "Peace", "m", "sky1543829");

        try {
            Connection conn = db.openConnection();

            AuthDAO eDao = new AuthDAO(conn);
            UserDAO userDAO = new UserDAO(conn);

            db.clearTables();

            userDAO.insertUser(bestUser);
            eDao.insert(myAuth);
            eDao.insert(myAuth2);

            db.closeConnection(true);
        }catch (DataAccessException e) {
            db.closeConnection(false);
        }


    }

    @Test
    public void VerificationPass() throws Exception {

        boolean isFound = false;


        AuthVerificationService verifyService = new AuthVerificationService();
        isFound = verifyService.verify(myAuth.getAuth_token());

        assertTrue(isFound);

        isFound = verifyService.verify(myAuth2.getAuth_token());

        assertTrue(isFound);

        isFound = verifyService.verify("GotTOBEKidding");

        assertFalse(isFound);


    }

}
