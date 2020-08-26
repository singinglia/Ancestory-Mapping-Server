package services;


import Dao.DataAccessException;
import Dao.Database;

import Dao.EventDAO;
import model.Event;
import requests.EventRequest;
import requests.MissingOrInvalidValueException;
import results.EventResult;


import java.sql.Connection;

public class EventService {
    /**
     * Retrieves event with eventID in EventRequest
     * @param request Event to retrieve from database
     * @return EventResult with event from database
     */
    public EventResult getEvent(EventRequest request){
        boolean isSuccessful = true;
        String errorMessage = "None";
        Event event = null;
        Database db = new Database();

        try {
            Connection conn = db.openConnection();

            if(request == null){
                throw new MissingOrInvalidValueException();
            }

            //Get Event
            EventDAO eDao = new EventDAO(conn);
            if (request.getEventID() != null) {
                event = eDao.find(request.getEventID());
            } else {
                throw new MissingOrInvalidValueException();
            }

            if (event == null) {
                throw new MissingOrInvalidValueException();
            }

            //Check if event authorized for user
            String authToken = request.getAuthToken();
            AuthVerificationService authService = new AuthVerificationService();
            String associatedUsername = authService.getUsername(authToken);
            if(associatedUsername == null){
                throw new AuthorizationException();
            }
            if (!associatedUsername.equals(event.getUsername())) {
                throw new AuthorizationException();
            }

        } catch (DataAccessException a) {
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
            EventResult result = event.toResult();
            result.setWasSuccessful(isSuccessful);
            return result;
        } else {
            return new EventResult(isSuccessful, errorMessage);
        }
    }
}
