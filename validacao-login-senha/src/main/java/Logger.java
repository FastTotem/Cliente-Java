import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

public class Logger {
    private static final int tamanhoMaximo = 5;
    private static final int maximoHistoricoArquivos = 30;
    private static final String logFile = "SystemComponent.log";
    private static final String logDir = "Diretório de trabalho atual: " + System.getProperty("user.dir");

    public static void main(String[] args) {

        logInfo("Versão do programa: 1.0", Logger.class);

        ProcessadorT processadorT = new ProcessadorT();
        MemoriaT memoriaT = new MemoriaT();

        // Inicia threads separadas para monitorar o uso do processador e da memória
        new Thread(processadorT::monitorarUsoProcessador).start();
        new Thread(memoriaT::monitorarUsoMemoria).start();

        Componente componentes = new Componente();
    }

       private static void checkLogRotation() throws IOException {
        long fileSize = new File(logFile).length();  // Verifica o tamanho do arquivo, se o tamanho do arquivo atingir o limite, faz a rotação
        /*Quando você define o tamanho máximo do arquivo em megabytes (MB),
        você precisa converter essa unidade para bytes para comparar com o tamanho real do arquivo,
        que geralmente é retornado em bytes.
        A razão pela qual você multiplica por 1024 duas vezes
        (1 kilobyte = 1024 bytes e 1 megabyte = 1024 kilobytes)
        é que há 1024 bytes em um kilobyte e 1024 kilobytes em um megabyte.*/
        if (fileSize > tamanhoMaximo * 1024 * 1024) {
            rotateLogs();
        }
    }

    private static void rotateLogs() throws IOException {
        // Renomeia o arquivo atual com um timestamp
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String rotatedFileName = logDir + "/app_" + timestamp + ".log";
        File currentLogFile = new File(logFile);
        File rotatedFile = new File(rotatedFileName);
        currentLogFile.renameTo(rotatedFile);

        // Cria um novo arquivo de log
        try (PrintWriter writer = new PrintWriter(new FileWriter(logFile, false))) {
            writer.println("Log rotated at " + timestamp);
        }
        // Remove arquivos antigos se houver muitos
        removeOldLogs();
    }

    private static void removeOldLogs() {
        File fileLogDir = new File(logDir);
        File[] files = fileLogDir.listFiles();

        if (files != null && files.length > maximoHistoricoArquivos) {
            Arrays.sort(files, Comparator.comparingLong(File::lastModified));
            for (int i = 0; i < files.length - maximoHistoricoArquivos; i++) {
                files[i].delete();
            }
        }
    }

    public static <T> void logSEVERE(String message, Class<T> clazz) {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String logEntry = timestamp + "SEVERE: " + message + Logger.class;
        System.out.println(logEntry); // Imprime no console

        // Salva no arquivo de log
        try (PrintWriter writer = new PrintWriter(new FileWriter("SystemComponent.txt", true))) {
            writer.println(logEntry);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <T> void logWarning(String message, Class<T> clazz) {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String logEntry = timestamp + " [" + clazz.getSimpleName() + "] - " + message;
        System.out.println(logEntry);
        // Salva no arquivo de log
        try {
            checkLogRotation();
            try (PrintWriter writer = new PrintWriter(new FileWriter(logFile, true))) {
                writer.println(logEntry);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
       public static <T> void logInfo(String message, Class<T> clazz) {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String logEntry = timestamp + " [" + clazz.getSimpleName() + "] " + message;
        System.out.println(logEntry);

        // Salva no arquivo de log
        try (PrintWriter writer = new PrintWriter(new FileWriter("SystemComponent.txt", true))) {
            writer.println(logEntry);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}