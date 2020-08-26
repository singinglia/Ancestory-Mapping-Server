package Handlers;

import Dao.DataAccessException;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import services.AllPersonService;
import requests.AllPersonRequest;
import results.AllPersonResult;
import results.EventResult;

import java.io.IOException;
import java.net.HttpURLConnection;

public class AllPersonRequestHandler extends AuthorizingRequestHandler {

    public void handle(HttpExchange exchange) throws IOException {
        //System.out.println("allPerson");
        setHttpExchange(exchange);
        try{
            if (isGET()) {
                Headers reqHeaders = exchange.getRequestHeaders();
                //Check Authorization -DONE
                //Get JSON STRING to return
                String userName = retrieveUsernameIfVerified(reqHeaders);
                AllPersonRequest pReq = new AllPersonRequest(userName);
                String respData = getPeople(pReq);
                sendResponse(respData);

            }

        } catch (IOException | DataAccessException e) {
            //Server Error
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            //Close Stream
            exchange.getResponseBody().close();

            // Display/log the stack trace
            e.printStackTrace();
        }

    }
    private String getPeople(AllPersonRequest pReq) throws IOException {
        Serializer serializer = new Serializer();

        AllPersonService personService = new AllPersonService();
        AllPersonResult personResult = personService.getPeople(pReq);

        String jsonStr = serializer.generate(personResult);

        success = personResult.getSuccess();

        return jsonStr;

    }
}
