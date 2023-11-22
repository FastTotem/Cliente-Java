package funcoes;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class DesligarTotem implements HttpHandler {
    @Override
    public void handle(HttpExchange t) throws IOException {
        String response = "Desligando totem";

        try {
            Process process = Runtime.getRuntime().exec("shutdown -s -t 3000");
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        t.sendResponseHeaders(200, response.length());
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
