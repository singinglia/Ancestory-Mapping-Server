package results;

public class LoginResult extends BasicResult{

    public LoginResult(boolean success, String message) {
        super(success, message);
    }

    public LoginResult(boolean success, String auth_token, String username, String personID) {
        super(success);
        this.authToken = auth_token;
        this.userName = username;
        this.personID = personID;
    }
    private String authToken;
    private String userName;
    private String personID;


    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getAuth_token() {
        return authToken;
    }

    public void setAuth_token(String auth_token) {
        this.authToken = auth_token;
    }

    public String getUsername() {
        return userName;
    }

    public void setUsername(String username) {
        this.userName = username;
    }
}
