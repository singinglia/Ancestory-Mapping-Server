package Handlers;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import services.EventService;
import requests.EventRequest;
import results.EventResult;
import results.PersonResult;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;

public class EventHandler extends AuthorizingRequestHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        //System.out.println("event");
        setHttpExchange(exchange);
        try{
            if (isGET()) {
                Headers reqHeaders = exchange.getRequestHeaders();
                //Check Authorization
                if(isValidAuthToken(reqHeaders)) {
                    //Get JSON STRING to return
                    URI myURI = exchange.getRequestURI();
                    String reqPath= myURI.getPath();
                    String eventID = getEventID(reqPath);
                    if(eventID == null){
                        AllEventRequestHandler allEventHandler = new AllEventRequestHandler();
                        allEventHandler.handle(exchange);
                    }
                    else {
                        EventRequest eventReq = new EventRequest(eventID, authToken);
                        String respData = getEvent(eventReq);
                        sendResponse(respData);
                    }
                } else{
                    EventResult result = new EventResult(success, "Authentication Error");
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
    private String getEvent(EventRequest eventReq) throws IOException{
        Serializer serializer = new Serializer();

        EventService eventService = new EventService();
        EventResult eventResult = eventService.getEvent(eventReq);

        String jsonStr = serializer.generate(eventResult);
        success = eventResult.getSuccess();

        return jsonStr;

    }

    private String getEventID(String URI){
        String[] paths = URI.split("/");
        if(paths.length == 3) {
            return paths[2];
        } else{
            return null;
        }
    }

    private String resultToJSON(EventResult result) throws IOException{
        Serializer serializer = new Serializer();
        return serializer.generate(result);
    }
}
