package funcoes;

import com.sun.net.httpserver.Headers;
import com.github.britooo.looca.api.group.sistema.Sistema;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class DesligarTotem implements HttpHandler {
    @Override
    public void handle(HttpExchange t) throws IOException {
        Headers headers = t.getResponseHeaders();
        headers.set("Access-Control-Allow-Origin", "*");
        headers.set("Content-Type", "application/json");
        
        Sistema sistema = new Sistema();

        if (sistema.getSistemaOperacional().toLowerCase().equals("windows")){
            try {
                Process process = Runtime.getRuntime().exec("shutdown -s -t 3000");
                process.waitFor();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        } else if (sistema.getSistemaOperacional().toLowerCase().equals("linux")){
            try {
                Process process = Runtime.getRuntime().exec("sudo shutdown -h now");
                process.waitFor();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }

        String response = "{ \"message\": \"Desligando totem\" }";

        t.sendResponseHeaders(200, response.length());
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
