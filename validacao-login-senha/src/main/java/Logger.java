import com.github.britooo.looca.api.group.discos.Disco;
import com.github.britooo.looca.api.group.discos.DiscoGrupo;
import com.github.britooo.looca.api.group.dispositivos.DispositivosUsbGrupo;
import conexao.Conexao;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Logger {
    private static final int tamanhoMaximo = 30;
    private static final int maximoHistoricoArquivos = 100;
    // Obtenha a data atual
    static Date dataAtual = new Date();

    // Defina o formato desejado para a data no nome do arquivo
    static SimpleDateFormat formatoData = new SimpleDateFormat("yyyyMMdd");

    // Formate a data atual conforme o formato desejado
    static String dataFormatada = formatoData.format(dataAtual);

    // Concatena a data formatada com o nome do arquivo
    private static final String logDir = "logs" + File.separator;
    private static final String logFile = logDir + "HardwareMonitoring[INFO]" + dataFormatada + ".log";

    private final Conexao conexao = new Conexao();
    private final JdbcTemplate con = conexao.getConexaoDoBanco();

    static {
        // Verifica se a pasta "logs" existe e cria se não existir
        File logsDir = new File(logDir);
        if (!logsDir.exists()) {
            if (!logsDir.mkdirs()) {
                throw new RuntimeException("Falha ao criar a pasta 'logs'.");
            }
        }
    }

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

    private static synchronized void log(String level, String message, Class<?> clazz) {
        SimpleDateFormat dataFormatada = new SimpleDateFormat("yyyyMMdd"); // Mova a definição para dentro do método
        String logFile = logDir + "HardwareMonitoring[INFO]" + dataFormatada.format(new Date()) + ".log"; // Atualize o nome do arquivo
        String logEntry = dataFormatada.format(new Date()) + " [" + clazz.getSimpleName() + "] " + level + ": " + message;
        try {
            checkLogRotation();
            try (PrintWriter writer = new PrintWriter(new FileWriter(logFile, true))) {
                writer.println(logEntry);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static <T> void logSevere(String message, Class<T> clazz) {
        log("SEVERE", message, clazz);
    }

    public static <T> void logWarning(String message, Class<T> clazz) {
        log("WARNING", message, clazz);
    }

    public static synchronized void logDiscoInfo(List<DiscoT> discos) {
        for (DiscoT discoT : discos) {
            logInfo("Disco Info: \n" + discoT.toString(), DiscoT.class);
        }
    }
    public static synchronized <T> void logInfo(String message, Class<T> clazz) {
        log("INFO", message, clazz);
    }
}
