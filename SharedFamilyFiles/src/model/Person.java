package model;

import requests.MissingOrInvalidValueException;
import results.PersonResult;

public class Person {

    private String personID;
    private String associatedUsername;
    private String firstName;
    private String lastName;
    private String gender;
    private String fatherID;
    private String motherID;
    private String spouseID;
    private Integer birthYear;


    public Person(String personID, String associatedUsername, String firstName, String lastName, String gender) {
        this.personID = personID;
        this.associatedUsername = associatedUsername;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }

    public Person(String personID, String associatedUsername, String firstName, String lastName, String gender, String fatherID, String motherID, String spouseID) {
        this.personID = personID;
        this.associatedUsername = associatedUsername;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFatherID() {
        return fatherID;
    }

    public void setFatherID(String fatherID) {
        this.fatherID = fatherID;
    }

    public String getMotherID() {
        return motherID;
    }

    public void setMotherID(String motherID) {
        this.motherID = motherID;
    }

    public String getSpouseID() {
        return spouseID;
    }

    public void setSpouseID(String spouseID) {
        this.spouseID = spouseID;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (o instanceof Person) {
            Person oPerson = (Person) o;
            boolean isEqual = true;
            if(oPerson.getPersonID().equals(getPersonID()) &&
                    oPerson.getFirstName().equals(getFirstName()) &&
                    oPerson.getLastName().equals(getLastName()) &&
                    oPerson.getGender().equals (getGender())){
                isEqual = true;
            }
            else{
                isEqual = false;
            }
            if (oPerson.getFatherID() != null){
                if(getFatherID() != null){
                    if(oPerson.getFatherID().equals(getFatherID())){
                        isEqual = true;
                    }
                    else{
                        return false;
                    }
                }
                else{
                    return false;
                }
            }
            if (oPerson.getMotherID() != null){
                if(getMotherID() != null){
                    if(oPerson.getMotherID().equals(getMotherID())){
                        isEqual = true;
                    }
                    else{
                        return false;
                    }
                }
                else{
                    return false;
                }
            }
            if (oPerson.getSpouseID() != null){
                if(getSpouseID() != null){
                    if(oPerson.getSpouseID().equals(getSpouseID())){
                        isEqual = true;
                    }
                    else{
                        return false;
                    }
                }
                else{
                    return false;
                }
            }
            return isEqual;
        } else {
            return false;
        }
    }

    public PersonResult personToResult() {
        return new PersonResult(personID, associatedUsername, firstName, lastName, gender,
                                                fatherID, motherID, spouseID);
    }
}
