package requests;

public class EventRequest extends AuthRequest{
    String eventID;

    public String getEventID() {
        return eventID;
    }

    public EventRequest(String eventID, String auth_Token) {
        super(auth_Token);
        this.eventID = eventID;
    }
}
