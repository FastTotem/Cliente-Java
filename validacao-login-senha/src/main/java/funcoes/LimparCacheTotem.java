package funcoes;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;

public class LimparCacheTotem implements HttpHandler {
    @Override
    public void handle(HttpExchange t) throws IOException {
        Headers headers = t.getResponseHeaders();
        headers.set("Access-Control-Allow-Origin", "*");
        headers.set("Content-Type", "application/json");

        String response = "{ \"message\": \"Limpando cache do totem\" }";

        String path = LimparCacheTotem.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        String decodedPath = URLDecoder.decode(path, "UTF-8");
        String pathzinElevate = String.format("""
                "%sElevate.exe"
                """,decodedPath.replace("FastTotemClient.jar", ""));
        String pathzinEmpty = String.format("""
                "%sEmptyStandbyList.exe"
                """,decodedPath.replace("FastTotemClient.jar", ""));

        String pathTrue = String.format("""
                "%s"
                """,decodedPath.replace("/FastTotemClient.jar", ""));

        try {
            String comando = String.format("""
                    cd %s
                    Elevate.exe schtasks /create /tn StandbyListTaskLendaria /tr EmptyStandbyList.exe /sc minute /mo 10 /ru SYSTEM""", pathTrue);
            System.out.println(comando);
            executarComandoNoShell(comando);
            System.out.println("Tarefa agendada criada com sucesso.");
        } catch (IOException e) {
            System.err.println("Erro: " + e.getMessage());
        }

        t.sendResponseHeaders(200, response.length());
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private static void executarComandoNoShell(String comando) throws IOException {
        List<String> comandoList = Arrays.asList("cmd", "/c", comando);
        ProcessBuilder processBuilder = new ProcessBuilder(comandoList);
        processBuilder.redirectErrorStream(true);

        Process processo = processBuilder.start();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(processo.getInputStream()))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                System.out.println(linha);  // Saída do comando
            }
        } catch (IOException e) {
            throw new IOException("Erro ao ler a saída do processo: " + e.getMessage());
        }

        // Aguarde o término do processo
        try {
            int exitCode = processo.waitFor();
            if (exitCode != 0) {
                throw new IOException("Erro ao executar o comando. Código de saída: " + exitCode);
            }
        } catch (InterruptedException e) {
            throw new IOException("Erro ao esperar o término do processo: " + e.getMessage());
        }
    }
}
