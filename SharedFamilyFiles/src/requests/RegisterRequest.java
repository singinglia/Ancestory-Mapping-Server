package requests;

import model.User;

public class RegisterRequest {

    public RegisterRequest(){}

    public RegisterRequest(String userName, String password, String email, String firstName, String lastName, String gender){
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }

    private String userName;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String gender;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public User toUserObj(String personID) throws MissingOrInvalidValueException{

        User newUser = new User(userName, password, email, firstName, lastName, gender, personID);

        if (userName == null ||
            password == null ||
            email == null    ||
            firstName == null||
            lastName == null ) {
            throw new MissingOrInvalidValueException();
        }

        if (gender.equals("m") || gender.equals("f")  || gender == "M" || gender == "F") {
            return newUser;
        }
        else{
            throw new MissingOrInvalidValueException();
        }
    }
}
