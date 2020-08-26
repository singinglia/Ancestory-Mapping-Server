package requests;

public class LoginRequest {

    private String userName;
    private String password;

    /**
     * Constructor for LoginRequest object
     * @param username for login
     * @param password for login, needs to match username
     */
    public LoginRequest(String username, String password){
        this.userName = username;
        this.password = password;
    }

    public String getUsername() {
        return userName;
    }

    public void setUsername(String username) {
        this.userName = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



}
