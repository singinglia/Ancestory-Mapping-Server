package model;

public class AuthToken {

    private String username;
    private String personID;
    private String auth_token;

    public AuthToken(){}

    public AuthToken(String username, String personID, String authToken){
        this.username = username;
        this.personID = personID;
        auth_token = authToken;
    }
    public AuthToken(String authToken, String username){
        this.username = username;
        auth_token = authToken;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getAuth_token() {
        return auth_token;
    }

    public void setAuth_token(String auth_token) {
        this.auth_token = auth_token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (o instanceof AuthToken) {
            AuthToken oAuth = (AuthToken) o;
            return oAuth.getAuth_token().equals(getAuth_token()) &&
                    oAuth.getUsername().equals(getUsername());

        } else {
            return false;
        }
    }

}
