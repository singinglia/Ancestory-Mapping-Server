package services;


import Dao.DataAccessException;
import Dao.Database;
import Dao.EventDAO;
import model.Event;
import requests.AllEventRequest;
import requests.MissingOrInvalidValueException;
import results.AllEventResult;
import results.AllPersonResult;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class AllEventService {

    /**
     * Retrieves all events in current users tree
     * @param request authtoken for user request
     * @return Result which has a JSONObect containing events
     */
    public AllEventResult getPeople(AllEventRequest request) {

        boolean success = true;
        String errorMessage = "None";
        ArrayList<Event> eventArray = null;
        Database db = new Database();


        try(Connection conn = db.openConnection()) {
            if(request == null){
                throw  new MissingOrInvalidValueException();
            }

            String associatedUsername = request.getUsername();

            //Get People
            EventDAO eDao = new EventDAO(conn);

            eventArray = eDao.findAllEvents(associatedUsername);

            //Check Success Send Result

            db.closeConnection(true);

        } catch (DataAccessException | SQLException a) {
            success = false;
            errorMessage = "Database access error";
        } catch (MissingOrInvalidValueException m){
            success = false;
            errorMessage = "Missing values error";
        }

        if (success) {
            AllEventResult result = new AllEventResult(success, eventArray);
            return result;
        } else {
            return new AllEventResult(success, errorMessage);
        }
    }
}
