package results;

public class BasicResult {
    protected String message;
    protected boolean success;

    public BasicResult(){}

    public BasicResult(boolean wasSuccessful, String resultMessage) {
        this.message = resultMessage;
        this.success = wasSuccessful;
    }

    public BasicResult(boolean wasSuccessful) {
        this.success = wasSuccessful;
    }

    public void setResultMessage(String resultMessage) {
        this.message = resultMessage;
    }

    public void setWasSuccessful(boolean wasSuccessful) {
        this.success = wasSuccessful;
    }

    public String getResultMessage() {
        return message;
    }

    public boolean getSuccess() {
        return success;
    }
}
