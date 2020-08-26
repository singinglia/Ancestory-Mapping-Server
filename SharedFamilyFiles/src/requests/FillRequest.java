package requests;

public class FillRequest {

    /**
     * Constructor
     *
     * @param username to fill tree for
     */
    public FillRequest(String username){
        numGenerations = 4;
        this.userName = username;
    }


    /**
     * Constructor
     *
     * @param username to fill tree for
     * @param numGens number of generations to generate
     */
    public FillRequest(String username, int numGens){
        numGenerations = numGens;
        this.userName = username;

    }
    private int numGenerations;
    private String userName;

    public int getNumGenerations() {
        return numGenerations;
    }

    public String getUsername() {
        return userName;
    }
}
