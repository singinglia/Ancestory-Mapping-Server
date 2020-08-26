package Handlers;

import com.sun.net.httpserver.HttpExchange;
import services.LoadService;
import requests.LoadRequest;
import results.LoadResult;


import java.io.IOException;
import java.net.HttpURLConnection;

public class LoadHandler extends POSTHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        //System.out.println("load");
        setHttpExchange(exchange);

        try {
            if(isPOST()){
                String reqBody = getRequestBody();
                String regResult = loadData(reqBody);
                sendResponse(regResult);
            }

        } catch (IOException e) {
            //Server Error
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
            exchange.getResponseBody().close();

            // Display/log the stack trace
            e.printStackTrace();
        }

    }

    private String loadData(String reqBody) throws IOException {
        Serializer serializer = new Serializer();

        LoadRequest loadRequest = serializer.deserialize(reqBody, LoadRequest.class);
        LoadService loadService = new LoadService();
        LoadResult loadResult = loadService.load(loadRequest);

        String jsonStr = serializer.generate(loadResult);

        success = loadResult.getSuccess();

        return jsonStr;
    }

}
