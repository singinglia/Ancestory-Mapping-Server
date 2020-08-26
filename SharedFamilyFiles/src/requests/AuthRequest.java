package requests;

public class AuthRequest {
    public AuthRequest(String authToken) {
        this.authToken = authToken;
    }

    String authToken;

    public String getAuthToken() {
        return authToken;
    }
}
