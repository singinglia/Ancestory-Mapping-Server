package services;

import Dao.DataAccessException;
import Dao.Database;
import results.ClearResult;
import results.RegisterResult;

import java.sql.Connection;
import java.sql.SQLException;

public class ClearService {

    /**
     * Clears all tables from the database
     *
     * @return ClearResult, whether clear was successful and message
     */
    public ClearResult clear(){
        boolean isSuccessful = true;
        String message = "Clear Succeeded";
        Database db = new Database();

        try(Connection conn = db.openConnection();) {

            db.clearTables();

            db.closeConnection(true);

        } catch (DataAccessException | SQLException e) {
            isSuccessful = false;
            message = "Database access error";
        }

        ClearResult result = new ClearResult(isSuccessful, message);
        return result;

    }
}
