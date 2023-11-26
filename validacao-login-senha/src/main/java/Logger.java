import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class Logger {
    private static final int tamanhoMaximo = 30;
    private static final int maximoHistoricoArquivos = 100;

    static Date dataAtual = new Date();
    static SimpleDateFormat formatoData = new SimpleDateFormat("yyyyMMdd");
    static String dataFormatada = formatoData.format(dataAtual);

    private static final String logDir = "logs" + File.separator;
    private static final String logFile = "HardwareInfo" + dataFormatada + ".log";

    public static String getLogFile() {
        return logFile;
    }

    private static void checkLogRotation() throws IOException {
        long fileSize = new File(logFile).length();
        if (fileSize > tamanhoMaximo * 1024 * 1024) {
            rotateLogs();
        }
    }

    private static void rotateLogs() throws IOException {
        String rotatedFileName = logDir + dataFormatada + ".log";
        File currentLogFile = new File(logFile);
        File rotatedFile = new File(rotatedFileName);
        currentLogFile.renameTo(rotatedFile);

        try (PrintWriter writer = new PrintWriter(new FileWriter(logFile, false))) {
            writer.println("Log rotated at " + dataFormatada);
        }

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

    public static synchronized void logDiscoInfo(List<DiscoT> discos) {
        for (DiscoT discoT : discos) {
            logInfo("Disco Info: \n" + discoT.toString(), DiscoT.class);
        }
    }

    public static synchronized <T> void logInfo(String message, Class<T> clazz) {
        String logEntry = dataFormatada + " [" + clazz.getSimpleName() + "] " + message;
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
}
