package services;

import Dao.*;
import model.Event;
import model.Person;
import model.User;
import requests.LoadRequest;
import requests.MissingOrInvalidValueException;
import results.LoadResult;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class LoadService {

    /**
     * Clears Data by calling clear and then Loads LoadRequest
     * @param request Person, user, and event ot Load
     * @return Message and Success
     */
    public LoadResult load(LoadRequest request){
        String message = "Success";
        boolean success = true;

        ArrayList<Event> events = request.getLoadEvents();
        ArrayList<Person> persons = request.getLoadPersons();
        ArrayList<User> users = request.getLoadUsers();

        int userCount = 0;
        int personCount = 0;
        int eventCount = 0;

        ClearService clearService = new ClearService();
        clearService.clear();

        Database db = new Database();

        try(Connection conn = db.openConnection();){

            if(request == null){
                throw  new MissingOrInvalidValueException();
            }

            UserDAO uDao = new UserDAO(conn);
            PersonDAO pDao = new PersonDAO(conn);
            EventDAO eDao = new EventDAO(conn);

            for(User user : users){
                uDao.insertUser(user);
                userCount++;
            }

            for(Person person : persons){
                pDao.insert(person);
                personCount++;
            }

            for(Event event : events){
                eDao.insert(event);
                eventCount++;
            }

            db.closeConnection(true);

        } catch (DataAccessException | SQLException e) {
            message = "Database Access Error";
            e.printStackTrace();
        }  catch (MissingOrInvalidValueException m){
            success = false;
            message = "Missing data error";
        }

        if(success) {
            message = "Successfully added " + userCount + " users, " + personCount + " persons, and " +
                    eventCount + " events to the database.";
        }
        LoadResult result = new LoadResult(success, message);
        return result;



    }
}
