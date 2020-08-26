package Handlers;

import Dao.DataAccessException;
import com.sun.net.httpserver.HttpExchange;
import requests.LoginRequest;
import services.LoginService;
import results.LoginResult;


import java.io.IOException;
import java.net.HttpURLConnection;

public class LoginHandler extends POSTHandler {


    @Override
    public void handle(HttpExchange exchange) throws IOException {
        //System.out.println("login");
        setHttpExchange(exchange);
        success = true;

        try {
            if(isPOST()){
                String reqBody = getRequestBody();
                String response = loginUser(reqBody);

                sendResponse(response);
            }

        } catch (IOException | DataAccessException e) {
            //Server Error
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
            exchange.getResponseBody().close();

            // Display/log the stack trace
            e.printStackTrace();
        }


    }

    private String loginUser(String regReq) throws IOException, DataAccessException {
        Serializer serializer = new Serializer();

        LoginRequest loginRequest = serializer.deserialize(regReq, LoginRequest.class);
        LoginService loginService = new LoginService();


        LoginResult loginResult = loginService.login(loginRequest);
        String jsonString = serializer.generate(loginResult);
        success = loginResult.getSuccess();

        return jsonString;

    }
}
