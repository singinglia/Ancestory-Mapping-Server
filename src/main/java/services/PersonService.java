package services;

import Dao.DataAccessException;
import Dao.Database;
import Dao.PersonDAO;
import model.Person;
import requests.MissingOrInvalidValueException;
import requests.PersonRequest;
import results.PersonResult;
import results.RegisterResult;

import java.sql.Connection;

public class PersonService {

    /**
     * Retrieves person with personID in PersonRequest
     * @param request Person to retrieve from database
     * @return PersonResult with person from database
     */
    public PersonResult getPerson(PersonRequest request) {
        boolean isSuccessful = true;
        String errorMessage = "None";
        Person person = null;
        Database db = new Database();

        try {
            Connection conn = db.openConnection();

            if(request == null){
                throw new MissingOrInvalidValueException();
            }

            //Get Person
            PersonDAO personDao = new PersonDAO(conn);
            if (request.getPersonId() != null) {
                person = personDao.getPerson(request.getPersonId());
            } else {
                throw new MissingOrInvalidValueException();
            }

            if (person == null) {
                throw new MissingOrInvalidValueException();
            }

            //Check if Event authorized for user
            String authToken = request.getAuthToken();
            AuthVerificationService authService = new AuthVerificationService();
            String associatedUsername = authService.getUsername(authToken);
            if((associatedUsername == null)){
                throw new AuthorizationException();
            }
            if (!associatedUsername.equals(person.getAssociatedUsername())) {
                throw new AuthorizationException();
            }


        } catch (DataAccessException e) {
            isSuccessful = false;
            errorMessage = "Database access error";
        } catch (MissingOrInvalidValueException m) {
            isSuccessful = false;
            errorMessage = "Invalid personID error";
        } catch (AuthorizationException m) {
            isSuccessful = false;
            errorMessage = "Not authorized to access this data error";
        } finally {
            try {
                db.closeConnection(true);
            } catch (DataAccessException e) {
                isSuccessful = false;
                errorMessage = "Database access error";
                e.printStackTrace();
            }
        }

        if (isSuccessful) {
            PersonResult result = person.personToResult();
            result.setWasSuccessful(isSuccessful);
            return result;
        } else {
            return new PersonResult(isSuccessful, errorMessage);
        }
    }
}
