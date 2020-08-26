package Handlers;

import Dao.DataAccessException;
import com.sun.net.httpserver.HttpExchange;
import services.RegisterService;
import requests.RegisterRequest;
import results.RegisterResult;

import java.io.IOException;
import java.net.HttpURLConnection;

public class RegisterHandler extends POSTHandler {


    @Override
    public void handle(HttpExchange exchange) throws IOException {
        //System.out.println("register");
        setHttpExchange(exchange);

        try {
            if(isPOST()){
                String reqBody = getRequestBody();
                String regResult = registerUser(reqBody);
                sendResponse(regResult);
            }

        } catch (IOException | DataAccessException e) {
            //Server Error
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
            exchange.getResponseBody().close();

            // Display/log the stack trace
            e.printStackTrace();
        }


    }

    private String registerUser(String regReq) throws IOException, DataAccessException {
        Serializer serializer = new Serializer();

        RegisterRequest registerRequest = serializer.deserialize(regReq, RegisterRequest.class);
        RegisterService registerService = new RegisterService();
        RegisterResult registerResult = registerService.register(registerRequest);

        String jsonStr = serializer.generate(registerResult);

        success = registerResult.getSuccess();

        return jsonStr;


    }
}
