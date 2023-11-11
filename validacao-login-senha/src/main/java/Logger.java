import com.github.britooo.looca.api.group.discos.Disco;


import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    public static void main(String[] args) {

        System.out.println("Diretório de trabalho atual: " + System.getProperty("user.dir"));

        ProcessadorT processadorT = new ProcessadorT();
        processadorT.monitorarUsoProcessador();
    }

    public static <T> void log(String message) {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String logEntry = timestamp + " [Processador] " + message;
        System.out.println(logEntry); // Imprime no console

        // Salva no arquivo de log
        try (PrintWriter writer = new PrintWriter(new FileWriter("log.txt", true))) {
            writer.println(logEntry);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}