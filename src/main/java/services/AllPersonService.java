package services;

import Dao.DataAccessException;
import Dao.Database;
import Dao.PersonDAO;
import model.Person;
import requests.AllPersonRequest;
import requests.MissingOrInvalidValueException;
import results.AllPersonResult;
import results.PersonResult;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class AllPersonService {

    /**
     * Retrieves all persons in current users tree
     * @param request authtoken for user request
     * @return Result which has a JSONObect containing persons
     */
    public AllPersonResult getPeople(AllPersonRequest request){
        boolean success = true;
        String errorMessage = "None";
        ArrayList<Person> personList = null;
        Database db = new Database();

        try(Connection conn = db.openConnection()) {
            if(request == null){
                throw  new MissingOrInvalidValueException();
            }

            String associatedUsername = request.getUsername();
            //Get Person
            PersonDAO personDao = new PersonDAO(conn);
            personList = personDao.getAllPersons(associatedUsername);

            db.closeConnection(true);

        } catch (DataAccessException | SQLException a) {
            success = false;
            errorMessage = "Database access error";
        }  catch (MissingOrInvalidValueException m){
            success = false;
            errorMessage = "Missing values error";
        }

        if (success) {
            AllPersonResult result = new AllPersonResult(success, personList);
            return result;
        } else {
            return new AllPersonResult(success, errorMessage);
        }
    }
}
