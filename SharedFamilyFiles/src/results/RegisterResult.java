package results;

import model.AuthToken;

public class RegisterResult extends BasicResult {

    public RegisterResult(Boolean success, String authToken, String username, String personID){
        super(success);
        this.authToken = authToken;
        userName = username;
        this.personID = personID;
    }

    public RegisterResult(Boolean success, String message){
        super(success, message);
    }

    public String authToken;
    public String userName;
    public String personID;

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }
}
