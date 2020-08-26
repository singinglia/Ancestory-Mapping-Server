package Handlers;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class POSTHandler extends RequestHandler {

    protected String getRequestBody() throws IOException{
            // Get the request body input stream
            InputStream reqBody = exchange.getRequestBody();
            //System.out.println("REQUEST BODY");
            String body = readString(reqBody);
            //System.out.println(body);

            // Read JSON string from the input stream
            return body;
    }

    private String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }

}
