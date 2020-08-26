package ServiceTest;

import Dao.DataAccessException;
import Dao.Database;
import Dao.UserDAO;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.LoginService;
import requests.LoginRequest;
import results.LoginResult;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class LoginServiceTest {


    private Database db;
    private LoginRequest loginCorrect;
    private LoginRequest loginWrong;
    private User bestUser;

    @BeforeEach
    public void setUp() throws Exception {


        db = new Database();
        bestUser = new User("sing4love", "123456", "fire@skyhigh.com", "Warren",
                "Peace", "m", "sky1543829");
        loginCorrect = new LoginRequest("sing4love", "123456");
        loginWrong = new LoginRequest("NotRight", "WRONG!!!");

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
    public void loginPass() throws Exception {

        LoginResult result = null;

        LoginService loginService = new LoginService();
        result = loginService.login(loginCorrect);

        assertNotNull(result);

        //HasAuthToken
        assertNotNull(result.getAuth_token());


    }

    @Test
    public void loginFail() throws Exception {

        LoginResult result = null;

        LoginService loginService = new LoginService();
        result = loginService.login(loginWrong);

        assertFalse(result.getSuccess());


    }
}
