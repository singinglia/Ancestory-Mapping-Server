package Handlers;

import com.sun.net.httpserver.HttpExchange;
import services.ClearService;
import results.ClearResult;

import java.io.IOException;
import java.net.HttpURLConnection;

public class ClearHandler extends POSTHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        //System.out.println("clear");
        setHttpExchange(exchange);

        try {
            if(isPOST()){
                ClearService clearService = new ClearService();
                ClearResult result = clearService.clear();
                Serializer serializer = new Serializer();
                String JSONResult = serializer.generate(result);
                success = result.getSuccess();

                sendResponse(JSONResult);
            }

        } catch (IOException e) {
            //Server Error
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
            exchange.getResponseBody().close();

            // Display/log the stack trace
            e.printStackTrace();
        }

    }
}
