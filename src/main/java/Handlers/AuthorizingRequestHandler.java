package Handlers;

import Dao.DataAccessException;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import services.AuthVerificationService;

import java.io.IOException;
import java.net.HttpURLConnection;

public class AuthorizingRequestHandler extends RequestHandler {

    String username;
    String authToken;

    public AuthorizingRequestHandler(){
        username= null;
    }


    protected Boolean isValidAuthToken(Headers headers) throws IOException{
        try{
            if (headers.containsKey("Authorization")) {
                authToken = headers.getFirst("Authorization");
                AuthVerificationService verifyService = new AuthVerificationService();
                if (verifyService.verify(authToken)) {
                    username = verifyService.getUsername(authToken);
                    return true;
                }
                else{
                    errorType = UNAUTHORIZED;
                    success = false;
                    return false;
                }
            }
            else {
                //AuthToken Not Valid or not Received
                errorType = UNAUTHORIZED;
                success = false;
                return false;
            }
        } catch (DataAccessException e) {
            //Server Error
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            //Close Stream
            exchange.getResponseBody().close();

            // Display/log the stack trace
            e.printStackTrace();
        }
        return false;
    }

    public String getUsername() {
        return username;
    }

    public String retrieveUsernameIfVerified(Headers headers) throws DataAccessException {

                authToken = headers.getFirst("Authorization");
                AuthVerificationService verifyService = new AuthVerificationService();
                username = verifyService.getUsername(authToken);
                return username;
    }

    public String getAuthToken(){
        return authToken;
    }
}
