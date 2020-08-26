package services;

import Dao.AuthDAO;
import Dao.DataAccessException;
import Dao.Database;
import Dao.UserDAO;
import model.AuthToken;
import model.User;
import requests.FillRequest;
import requests.MissingOrInvalidValueException;
import requests.RegisterRequest;
import results.RegisterResult;
import results.UsernameTakenException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class RegisterService extends BasicService{

    /**
     *Creates a new user account, calls <code> FillService </code> to generate 4 generations of ancestor data for the new
     * user, logs the user in by calling <code>LoginService</code> and it receives from loginService returns an auth token.
     *
     * @param request takes a request with user data to process
     *
     * @return RegisterResult, which contains an authToken
     */
    public RegisterResult register(RegisterRequest request) {
        boolean isSuccessful = true;
        String errorMessage = "None";
        String authTokenID = null;
        User userToAdd = null;
        String userID = null;
        Database db = new Database();

        System.out.println("Client Connected");

        try(Connection conn = db.openConnection();){

            if(request == null){
                throw  new MissingOrInvalidValueException();
            }

            //Add User to Database

            userID = generateID();
            userToAdd = request.toUserObj(userID);

            //Associate AuthToken with User
            authTokenID = generateID();
            AuthToken authToken = new AuthToken(authTokenID, request.getUserName());


            UserDAO userDao = new UserDAO(conn);
            if(userDao.find(userToAdd.getUsername()) == null) {
                userDao.insertUser(userToAdd);
            } else{
                throw new UsernameTakenException();
            }

            AuthDAO authDao = new AuthDAO(conn);
            authDao.insert(authToken);

            db.closeConnection(true);


        } catch (DataAccessException | SQLException a) {
            isSuccessful = false;
            errorMessage = "Database access error";
        } catch (MissingOrInvalidValueException a) {
            isSuccessful = false;
            errorMessage = "Missing value or data invalid error";
        } catch (UsernameTakenException e) {
            isSuccessful = false;
            errorMessage = "Username Error: This username is taken. Please pick another.";
            e.printStackTrace();
        }

        try{
            if(isSuccessful) {
                FillService fillService = new FillService();
                FillRequest fillRequest = new FillRequest(request.getUserName());
                fillService.fill(fillRequest);
            }
        } catch (IOException e) {
            isSuccessful = false;
            errorMessage = "Server error in Fill";
            e.printStackTrace();
        } catch (DataAccessException e) {
            isSuccessful = false;
            errorMessage = "Database error in Fill";
            e.printStackTrace();
        }

        if(isSuccessful) {
            RegisterResult result = new RegisterResult(isSuccessful, authTokenID, userToAdd.getUsername(), userID);
            return result;
        }
        else{
            RegisterResult result = new RegisterResult(isSuccessful, errorMessage);
            return result;
        }
    }
}

