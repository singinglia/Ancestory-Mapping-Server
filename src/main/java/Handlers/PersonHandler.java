package Handlers;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import services.PersonService;
import requests.PersonRequest;
import results.PersonResult;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;

public class PersonHandler extends AuthorizingRequestHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        //System.out.println("person");
        setHttpExchange(exchange);
        try{
            if (isGET()) {
                Headers reqHeaders = exchange.getRequestHeaders();
                //Check Authorization
                if(isValidAuthToken(reqHeaders)) {
                    //Get JSON STRING to return
                    URI myURI = exchange.getRequestURI();
                    String reqPath = myURI.getPath();
                    String authToken = reqHeaders.getFirst("Authorization");
                    String personID = getPersonID(reqPath);
                    if (personID == null) {
                        AllPersonRequestHandler allPerson = new AllPersonRequestHandler();
                        allPerson.handle(exchange);
                    } else {
                        PersonService personService = new PersonService();
                        PersonRequest request = new PersonRequest(personID, authToken);
                        PersonResult result = personService.getPerson(request);
                        success = result.getSuccess();
                        sendResponse(resultToJSON(result));
                    }
                } else {
                    PersonResult result = new PersonResult(success, "Authentication Error");
                    sendResponse(resultToJSON(result));
                }
            }

        } catch (IOException e) {
            //Server Error
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            //Close Stream
            exchange.getResponseBody().close();

            // Display/log the stack trace
            e.printStackTrace();
        }

    }


    private String resultToJSON(PersonResult result) throws IOException{
        Serializer serializer = new Serializer();
        return serializer.generate(result);
    }

    private String getPersonID(String URI){
        String[] paths = URI.split("/");
        if(paths.length == 3) {
            return paths[2];
        }
        else{
            return null;
        }
    }

}
