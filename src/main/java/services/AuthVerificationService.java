package services;

import Dao.AuthDAO;
import Dao.DataAccessException;
import Dao.Database;
import model.AuthToken;

import java.sql.Connection;
import java.sql.SQLException;

public class AuthVerificationService {

    public boolean verify(String authTokenID) throws DataAccessException{

        Database db = new Database();

        try(Connection conn = db.openConnection()) {

            AuthDAO authDao = new AuthDAO(conn);
            AuthToken authToken = authDao.find(authTokenID);

            db.closeConnection(true);

            if (authToken != null) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException();
        }

    }

    public String getUsername(String authTokenID) throws DataAccessException{

        Database db = new Database();
        Connection conn = db.openConnection();
        AuthDAO authDao = new AuthDAO(conn);
        AuthToken authToken = authDao.find(authTokenID);

        db.closeConnection(true);
        if(authToken != null) {
            return authToken.getUsername();
        } else{
            return null;
        }
    }
}
