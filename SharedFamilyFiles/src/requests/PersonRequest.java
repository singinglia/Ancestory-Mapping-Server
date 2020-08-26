package requests;

public class PersonRequest extends AuthRequest {

    public PersonRequest(String personId, String authToken) {
        super(authToken);
        this.authToken = authToken;
        this.personId = personId;
    }

    public String getPersonId() {
        return personId;
    }

    public String getAuthToken() {
        return authToken;
    }

    String personId;
    String authToken;

}
