package results;

public class FillResult {
    protected String message;
    protected boolean success;

    public FillResult(boolean success, String message) {
        this.message = message;
        this.success = success;
    }

    public boolean getSuccess() {
        return success;
    }

    public String getResultMessage(){
        return message;
    }
}
