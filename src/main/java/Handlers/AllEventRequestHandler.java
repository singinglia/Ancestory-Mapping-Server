package Handlers;

import Dao.DataAccessException;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import services.AllEventService;
import requests.AllEventRequest;
import results.AllEventResult;
import results.EventResult;

import java.io.IOException;
import java.net.HttpURLConnection;

public class AllEventRequestHandler extends AuthorizingRequestHandler {

    public void handle(HttpExchange exchange) throws IOException {
        //System.out.println("allevents");
        setHttpExchange(exchange);
        try{
            if (isGET()) {
                Headers reqHeaders = exchange.getRequestHeaders();
                //Check Authorization -DONE
                //Get JSON STRING to return
                String userName = retrieveUsernameIfVerified(reqHeaders);
                AllEventRequest eReq = new AllEventRequest(userName);
                String respData = getEvents(eReq);
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
    private String getEvents(AllEventRequest eventReq) throws IOException {
        Serializer serializer = new Serializer();

        AllEventService eventService = new AllEventService();
        AllEventResult eventResult = eventService.getPeople(eventReq);

        String jsonStr = serializer.generate(eventResult);

        success = eventResult.getSuccess();

        return jsonStr;

    }
}
