package services;

import Dao.*;
import model.Event;
import model.Person;
import model.User;
import requests.FillRequest;
import results.FillResult;
import results.RegisterResult;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Random;

public class FillService {
    private int MAX_AGE = 120;
    private int YOUNGEST_MARRIAGE_AGE = 15;
    private int MIN_MOTHERHOOD_AGE = 14;
    private int OLDEST_CHILD_BEARING_AGE = 50;
    private int OLDEST_FATHERING_AGE = 70;
    private String MALE = "m";
    private String FEMALE = "f";
    private String username;
    private ArrayList<Event> ancestorEvents;
    private ArrayList<Person> ancestors;
    private GeneratorService generator;
    private Connection conn;
    private ArrayList<Person> currentGeneration;
    private int personCount = 0;
    private int eventCount = 0;

    private int numGenerations;

    public FillService() throws IOException {
        numGenerations = 4;
        generator = new GeneratorService();
        currentGeneration = new ArrayList<Person>();
        ancestorEvents = new ArrayList<Event>();
        ancestors = new ArrayList<Person>();
    }

    /**
     * Logs in the user and returns an authToken
     *
     * @param request takes a request to process
     *
     * @return LoginResult, which contains an authToken
     */
    public FillResult fill(FillRequest request) throws DataAccessException {
        ancestorEvents.clear();
        ancestors.clear();
        numGenerations = request.getNumGenerations();
        username = request.getUsername();
        boolean success = true;
        String message = "Success";
        Database db = new Database();
        personCount = 0;
        eventCount = 0;

        try {
            conn = db.openConnection();
            generateAncestors();

            //Add Ancestors to Database
            PersonDAO personDAO = new PersonDAO(conn);
            EventDAO eventDAO = new EventDAO(conn);

            for(Person ancestor: ancestors){
                personDAO.insert(ancestor);
            }

            for(Event event: ancestorEvents){
                eventDAO.insert(event);
            }



    } catch (IOException e) {
            success = false;
            message = "Server Error";
            e.printStackTrace();
            db.closeConnection(false);
    } catch (UserNotFoundException u){
            success = false;
            message = "User was not found error";
            db.closeConnection(false);
        } catch (DataAccessException e){
            success = false;
            message = "Database Access Error";
        }
        finally{
            try{
                db.closeConnection(true);
            } catch (DataAccessException e) {
                success = false;
                message = "Database Access Error";
                e.printStackTrace();
            }
        }
        if(success) {
            message = "Successfully added " + personCount + " persons and " + eventCount + " events to the database.";
            FillResult result = new FillResult(success, message);
            return result;
        }
        else{
            FillResult result = new FillResult(success, message);
            return result;
        }
    }

    /**
     * Generates ancestors to add to tree
     */
    private void generateAncestors() throws DataAccessException, IOException, UserNotFoundException {

        Person user = genUserPerson();
        int userBirthYear = userBirthEvent(user.getPersonID());
        user.setBirthYear(userBirthYear);
        ArrayList<Person> nextGeneration = new ArrayList<Person>();
        currentGeneration.add(user);
        for(int i = 0; i <= numGenerations; i ++) {
            for (Person person : currentGeneration) {
                if( i != numGenerations) {
                    ArrayList<Person> parents = generateParents(person.getBirthYear());
                    for (Person parent : parents) {
                        if (parent.getGender().equals(MALE)) {
                            person.setFatherID(parent.getPersonID());
                        } else {
                            person.setMotherID(parent.getPersonID());
                        }
                        nextGeneration.add(parent);
                    }
                }
                ancestors.add(person);
                personCount++;
            }
            currentGeneration.clear();
            for (Person nextGen : nextGeneration) {
                currentGeneration.add(nextGen);
            }
            nextGeneration.clear();
        }
    }

    private ArrayList<Person> generateParents(int childBirthYear) throws IOException {
        int MOTHER_INDEX = 0;
        int FATHER_INDEX = 1;
        //Create Mother
        Person mother = generator.generatePerson(username, FEMALE);

        //Create Father
        Person father = generator.generatePerson(username, MALE);
        //Set Spouse IDs
        father.setSpouseID(mother.getPersonID());
        mother.setSpouseID(father.getPersonID());

        ArrayList<Integer> birthYears = generateCoupleEvents(mother.getPersonID(), father.getPersonID(), childBirthYear);

        mother.setBirthYear(birthYears.get(MOTHER_INDEX));
        father.setBirthYear(birthYears.get(FATHER_INDEX));

        ArrayList<Person> parents = new ArrayList<Person>();
        parents.add(mother);
        parents.add(father);

        return parents;

    }

    private Person genUserPerson() throws DataAccessException, UserNotFoundException {
        UserDAO userDAO = new UserDAO(conn);
        if(userDAO == null){
            throw new UserNotFoundException();
        }
        EventDAO eDAO = new EventDAO(conn);
        //Clear out Current People and Events for user
        eDAO.clearUserEvents(username);
        User userU = userDAO.find(username);
        Person user = new Person(userU.getPersonID(), username, userU.getFirstName(), userU.getLastName(), userU.getGender());
        return user;
    }

    public ArrayList<Integer> generateCoupleEvents(String motherID, String fatherID, int childBirthYear){

        //Birth Mother
        String BIRTH = "Birth";
        int motherBirthYear = getRandomNumberInRange((childBirthYear-OLDEST_CHILD_BEARING_AGE), (childBirthYear-MIN_MOTHERHOOD_AGE));
        Event motherBirth =generator.generateEvent(username, motherID, BIRTH, motherBirthYear);
        ancestorEvents.add(motherBirth);

        //Birth Father
        int motherChildBirthAge = childBirthYear - motherBirthYear;
        int fatherBirthYear = motherBirthYear;
        if (motherChildBirthAge > 20) {
            fatherBirthYear = motherBirthYear + getRandomNumberInRange(-20, 20);
            while((childBirthYear- fatherBirthYear) < 13){
                fatherBirthYear = motherBirthYear + getRandomNumberInRange(-20, 20);
            }
        }
        else {
            fatherBirthYear = getRandomNumberInRange((motherBirthYear-20), (childBirthYear - MIN_MOTHERHOOD_AGE));
        }
        ancestorEvents.add(generator.generateEvent(username, fatherID, BIRTH, fatherBirthYear));

        int parentBirthYear = fatherBirthYear;
        //Marriage
        if(motherBirthYear < fatherBirthYear){
            parentBirthYear = motherBirthYear;
        }

        int marriageYear = parentBirthYear + getRandomNumberInRange(YOUNGEST_MARRIAGE_AGE, MAX_AGE - 25);
        while(((marriageYear-motherBirthYear) < 13) | (marriageYear - fatherBirthYear < 13)){
            marriageYear = parentBirthYear + getRandomNumberInRange(YOUNGEST_MARRIAGE_AGE, MAX_AGE - 25);
        }

        String MARRIAGE = "Marriage";
        Event marriage = generator.generateEvent(username, motherID, MARRIAGE, marriageYear);
        ancestorEvents.add(marriage);
        Event FatherMarriage = new Event(generator.generateID(), username, fatherID, marriage.getLatitude(), marriage.getLongitude(),
                                        marriage.getCountry(), marriage.getCity(), MARRIAGE, marriage.getYear());

        ancestorEvents.add(FatherMarriage);


        int deathYearMin = childBirthYear;
        if(childBirthYear < marriageYear){
            deathYearMin = marriageYear;
        }

        //Death
        int motherDeathYear = getRandomNumberInRange(deathYearMin, (MAX_AGE + motherBirthYear));
        int fatherDeathYear =getRandomNumberInRange(deathYearMin, (MAX_AGE + fatherBirthYear));
        String DEATH = "Death";
        ancestorEvents.add(generator.generateEvent(username, motherID, DEATH, motherDeathYear));
        ancestorEvents.add(generator.generateEvent(username, fatherID, DEATH, fatherDeathYear));

        //WHAT IS UP WITH THIS?
        eventCount = eventCount + 6;

        ArrayList<Integer> birthYears = new ArrayList<Integer>();
        birthYears.add(motherBirthYear);
        birthYears.add(fatherBirthYear);

        return birthYears;
    }

    private Integer userBirthEvent(String personID){
        int CURRENT_YEAR = 2020;
        int MAX_CURR_AGE = 70;
        int MIN_CURR_AGE = 8;
        int birthYear = CURRENT_YEAR - getRandomNumberInRange(MIN_CURR_AGE, MAX_CURR_AGE);

        //Birth
        String BIRTH = "Birth";
        Event birth =generator.generateEvent(username, personID, BIRTH, birthYear);
        ancestorEvents.add(birth);
        eventCount++;

        return birthYear;
    }

    private int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            System.out.println(min + ", " + max);
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public ArrayList<Event> getAncestorEvents() {
        return ancestorEvents;
    }


}
