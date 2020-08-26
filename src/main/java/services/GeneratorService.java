package services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jsonModels.Location;
import jsonModels.LocationList;
import jsonModels.NameList;
import model.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.UUID;
import java.util.Random;

public class GeneratorService {
    private NameList femaleNames;
    private NameList maleNames;
    private NameList surNames;
    private LocationList locationOptions;
    private String LOCATION_JSON = "json/locations.json";
    private String FEMALE_NAMES = "json/fnames.json";
    private String MALE_NAMES = "json/mnames.json";
    private String SURNAMES = "json/snames.json";

    public GeneratorService() throws IOException {
        File locFile = new File(LOCATION_JSON);
        parseLocations(locFile);

        File fNameFile = new File(FEMALE_NAMES);
        femaleNames = parseNames(fNameFile);

        File mNameFile = new File(MALE_NAMES);
        maleNames = parseNames(mNameFile);

        File sNameFile = new File(SURNAMES);
        surNames = parseNames(sNameFile);
    }

    public String generateID(){
        return UUID.randomUUID().toString();
    }

    public Person generateBasePerson(String username){
        String personID = generateID();
        String associatedUsername = username;

        String gender;
        String firstName;
        Random r = new Random();
        int g = r.nextInt(2);
        if (g == 0){
            gender = "f";
            firstName = femaleNames.getNames().get(r.nextInt(femaleNames.getNames().size()));
        }
        else{
            gender = "m";
            firstName = maleNames.getNames().get(r.nextInt(maleNames.getNames().size()));
        }
        String lastName = surNames.getNames().get(r.nextInt(surNames.getNames().size()));
        return new Person(personID, associatedUsername, firstName, lastName, gender);
    }

    public Person generatePerson(String username, String gender){
        String personID = generateID();
        String firstName;
        Random r = new Random();
        if (gender.equals("f")){
            firstName = femaleNames.getNames().get(r.nextInt(femaleNames.getNames().size()));
        }
        else{
            firstName = maleNames.getNames().get(r.nextInt(maleNames.getNames().size()));
        }
        String lastName = surNames.getNames().get(r.nextInt(surNames.getNames().size()));
        return new Person(personID, username, firstName, lastName, gender);
    }

    public Event generateEvent(String username, String person_id, String event_type, int year){
        String eventID = generateID();

        Random r = new Random();
        Location eventLocal = locationOptions.getLocations().get(r.nextInt(locationOptions.getLocations().size()));
        float latitude = eventLocal.getLatitude();
        float longitude = eventLocal.getLongitude();
        String country = eventLocal.getCountry();
        String city = eventLocal.getCity();
        return new Event(eventID, username, person_id, latitude, longitude, country, city, event_type, year);
    }

    private void parseLocations(File file) throws IOException {
        try(FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            Gson gson = new Gson();
            locationOptions = gson.fromJson(bufferedReader, LocationList.class);
        }
    }

    private NameList parseNames(File file) throws IOException {
        try(FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();

            Gson gson = builder.create();
            return gson.fromJson(bufferedReader, NameList.class);
        }
    }
}
