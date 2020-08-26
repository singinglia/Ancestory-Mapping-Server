package Handlers;

import Dao.DataAccessException;
import com.sun.net.httpserver.HttpExchange;
import services.FillService;
import requests.FillRequest;
import requests.PersonRequest;
import results.FillResult;
import results.PersonResult;

import java.io.IOException;
import java.net.URI;

public class FillHandler extends POSTHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        //System.out.println("fill");
        setHttpExchange(exchange);

        if(isPOST()){
            try {
                //Get JSON STRING to return
                URI myURI = exchange.getRequestURI();
                String reqPath= myURI.getPath();
                String[] URIparts = parseURI(reqPath);
                String username = URIparts[2];
                int numGenerations = 4;
                if(URIparts.length == 4){
                    numGenerations = Integer.parseInt(URIparts[3]);
                }
                FillService fillService = new FillService();
                FillRequest request = new FillRequest(username, numGenerations);
                FillResult result = fillService.fill(request);
                success = result.getSuccess();
                sendResponse(resultToJSON(result));
            } catch (DataAccessException e) {
                e.printStackTrace();
            }
        }


    }

    private String resultToJSON(FillResult result) throws IOException{
        Serializer serializer = new Serializer();
        return serializer.generate(result);
    }

    private String[] parseURI(String URI){
        String[] paths = URI.split("/");
        return paths;
    }
}
