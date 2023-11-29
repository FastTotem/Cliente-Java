package funcoes;

import com.sun.net.httpserver.Headers;
import com.github.britooo.looca.api.group.sistema.Sistema;
import com.sun.jna.Platform;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import oshi.SystemInfo;

import java.io.IOException;
import java.io.OutputStream;

public class ReiniciarTotem implements HttpHandler {
    @Override
    public void handle(HttpExchange t) throws IOException {
        Headers headers = t.getResponseHeaders();
        headers.set("Access-Control-Allow-Origin", "*");
        headers.set("Content-Type", "application/json");

        String response = "{ \"message\": \"Reiniciando totem\" }";

        Sistema sistema = new Sistema();

        if (sistema.getSistemaOperacional().toLowerCase().equals("windows")){
            try {
                Process process = Runtime.getRuntime().exec("shutdown -r -t 0");
                process.waitFor();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        } else if (sistema.getSistemaOperacional().toLowerCase().equals("linux")){
            try {
                Process process = Runtime.getRuntime().exec("sudo reboot");
                process.waitFor();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }

        t.sendResponseHeaders(200, response.length());
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
