package Handlers;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;


public class RequestHandler implements HttpHandler {
    protected HttpExchange exchange;
    protected boolean success = true;
    protected Integer errorType;
    protected final Integer UNAUTHORIZED = 1;
    protected final Integer BAD_REQUEST = 2;
    protected final Integer SERVER_ERROR = 3;

    @Override
    public void handle(HttpExchange exchange) throws IOException {

    }
    protected RequestHandler(){
        super();
        success = true;
    }

    protected void setHttpExchange(HttpExchange exchange){
        this.exchange = exchange;
    }

    protected boolean isGET() throws IOException {
        try{
            if (exchange.getRequestMethod().toUpperCase().equals("GET")) {
                return true;
            }
            else {
                //Not a Get
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                return false;
            }

        } catch (IOException e) {
            //Server Error
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
            //Close Stream
            exchange.getResponseBody().close();
        }
        return false;
    }

    protected boolean isPOST() throws IOException {
        try{
            if (exchange.getRequestMethod().toUpperCase().equals("POST")) {
                return true;
            }
            else {
                //Not a post
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                return false;
            }

        } catch (IOException e) {
            //Server Error
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
            //Close Stream
            exchange.getResponseBody().close();
        }
        return false;
    }

    protected void sendResponse(String respData) throws IOException{
        try {
            //System.out.println(respData);
            if(success) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            } else {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            }

            OutputStream respBody = exchange.getResponseBody();
            writeString(respData, respBody);
            respBody.close();
        } catch (IOException e) {
            //Server Error
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
            //Close Stream
            exchange.getResponseBody().close();
        }
    }



    protected void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(sw);
        bw.write(str);
        bw.flush();
    }

}
