package services;

import Dao.AuthDAO;
import Dao.DataAccessException;
import Dao.Database;
import Dao.UserDAO;
import model.AuthToken;
import model.User;
import requests.LoginRequest;
import results.LoginResult;
import results.RegisterResult;

import java.sql.Connection;
import java.sql.SQLException;

public class LoginService extends BasicService {

    /**
     * Logs in the user and returns an authToken
     *
     * @param request takes a request to process
     *
     * @return LoginResult, which contains an authToken
     */
    public LoginResult login(LoginRequest request) {
        boolean isSuccessful = true;
        String errorMessage = "None";
        User userLookUp = new User();
        Database db = new Database();

        System.out.println("Client Connected");

        //Associate AuthToken with User
        String authTokenID = generateID();
        AuthToken authToken = new AuthToken(authTokenID, request.getUsername());


        try(Connection conn = db.openConnection()) {

            UserDAO userDao = new UserDAO(conn);
            userLookUp = userDao.find(request.getUsername());

            if(userLookUp == null){
                throw new UserNotFoundException();
            }

            if(userLookUp.getPassword().equals(request.getPassword())) {

                AuthDAO authDao = new AuthDAO(conn);
                authDao.insert(authToken);

            }
            else{
                throw new AuthorizationException();
            }

            db.closeConnection(true);

        } catch (DataAccessException | SQLException a) {
            isSuccessful = false;
            errorMessage = "Database access error";
            checkConnection(db);

        } catch (AuthorizationException a) {
            isSuccessful = false;
            errorMessage = "Wrong Password. Please try again.";
            checkConnection(db);
        }catch (UserNotFoundException u) {
            isSuccessful = false;
            errorMessage = "User not found. Please Register.";
            checkConnection(db);
        }


        if(isSuccessful) {
            LoginResult result = new LoginResult(isSuccessful, authTokenID, request.getUsername(), userLookUp.getPersonID());
            return result;
        }
        else{
            LoginResult result = new LoginResult(isSuccessful, errorMessage);
            return result;
        }
    }

    private void checkConnection(Database db){
        if (!db.isOpen()){
            System.out.println("Connection closed by try");
        }
    }


}
