import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
public class SharedLogger {
    private static final String logFile = "SystemComponent.log";
    public static synchronized <T> void logInfo(String message, Class<T> clazz) {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String logEntry = timestamp + " [" + clazz.getSimpleName() + "] " + message;
        System.out.println(logEntry);

        // Salva no arquivo de log
        try (PrintWriter writer = new PrintWriter(new FileWriter(logFile, true))) {
            writer.println(logEntry);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}