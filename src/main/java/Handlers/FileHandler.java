package Handlers;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileHandler implements HttpHandler {


    @Override
    public void handle(HttpExchange exchange) throws IOException {
        URI myURI = exchange.getRequestURI();
        String reqPath= myURI.getPath();

        String URIpath;

        if(reqPath.equals("/")) {
            URIpath = "web/index.html";
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        }

        else {

            URIpath = "web" + exchange.getRequestURI();
            if (URIpath.equals("web/index.html") | URIpath.equals("web/css/main.css")|
                    URIpath.equals("web/favicon.ico") | URIpath.equals("web/favicon.jpg") ){
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

            } else{
                URIpath = "web/HTML/404.html";
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
            }
        }

        Path filePath = FileSystems.getDefault().getPath(URIpath);


        //OutputStream respBody = exchange.getResponseBody();
        Files.copy(filePath, exchange.getResponseBody());
        exchange.getResponseBody().close();

    }
}
