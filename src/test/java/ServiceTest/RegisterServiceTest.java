package ServiceTest;

import Dao.Database;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.ClearService;
import services.RegisterService;
import requests.RegisterRequest;
import results.RegisterResult;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RegisterServiceTest {

    private Database db;
    private RegisterRequest request1;
    private RegisterRequest loginWrong;
    private User bestUser;


    @BeforeEach
    public void setUp() throws Exception {

        ClearService clear = new ClearService();
        clear.clear();

        db = new Database();
        bestUser = new User("sing4love", "123456", "fire@skyhigh.com", "Warren",
                "Peace", "m", "sky1543829");
        request1 = new RegisterRequest("sing4love", "123456", "fire@skyhigh.com", "Warren",
                "Peace", "m");
        loginWrong = new RegisterRequest("iSeeFire", "smog", "fire@middleearth.com", "Thorrin",
                "Oakenshield", "m");

    }


    @Test
    public void registerPass() throws Exception {

        RegisterResult result = null;

        RegisterService registerService = new RegisterService();
        result = registerService.register(request1);

        assertNotNull(result);

        //HasAuthToken
        assertNotNull(result.getAuthToken());


    }

    @Test
    public void registerFail() {


            RegisterResult result = null;
            RegisterRequest nullRequest = null;

            RegisterService registerService = new RegisterService();
            result = registerService.register(nullRequest);

            assertFalse(result.getSuccess());

            registerService.register(request1);
            RegisterResult result2 = registerService.register(request1);
            assertFalse(result2.getSuccess());

    }
}
