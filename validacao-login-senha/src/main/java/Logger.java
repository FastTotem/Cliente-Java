import com.github.britooo.looca.api.group.discos.Disco;
import com.github.britooo.looca.api.group.discos.DiscoGrupo;
import com.github.britooo.looca.api.group.dispositivos.DispositivosUsbGrupo;
import conexao.Conexao;
import org.springframework.jdbc.core.JdbcTemplate;

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

    // Obtenha a data atual
    static Date dataAtual = new Date();

    // Defina o formato desejado para a data no nome do arquivo
    static SimpleDateFormat formatoData = new SimpleDateFormat("yyyyMMdd_HHmmss");

    // Formate a data atual conforme o formato desejado
    static String dataFormatada = formatoData.format(dataAtual);

    // Concatena a data formatada com o nome do arquivo
    private static final String logFile = "SystemComponent[INFO]" + dataFormatada + ".log";
    private static final String logDir = "Diretório de trabalho atual: " + System.getProperty("user.dir");

    public static void main(String[] args) throws Exception {

        logInfo("Versão do programa: 1.0", Logger.class);

        ProcessadorT processadorT = new ProcessadorT();
        MemoriaT memoriaT = new MemoriaT();
        MaquinaT maquinaT = new MaquinaT();
        DiscoGrupo discoGrupo = new DiscoGrupo();
        DispositivosUsbGrupo usbs = new DispositivosUsbGrupo();
        UsbT usbT = new UsbT(usbs);

        // Cria uma instância de Conexao para obter o JdbcTemplate
        Conexao conexao = new Conexao();
        JdbcTemplate jdbcTemplate = conexao.getConexaoDoBanco();

        usbT.setJdbcTemplate(jdbcTemplate);

        // Inicia threads separadas para monitorar cada componente
        new Thread(processadorT::monitorarUsoProcessador).start();
        new Thread(memoriaT::monitorarUsoMemoria).start();
        new Thread(maquinaT::monitorarTempoAtividade).start();
        new Thread(usbT::logUsbDevices).start();
        new Thread(() -> {
            try {
                while (true) {
                    List<Disco> discos = discoGrupo.getDiscos(); // Obtém a lista de discos
                    logDiscoInfo(discos); // Chama o método passando a lista de discos
                    Thread.sleep(10000);// Aguarda 10 segundos antes de verificar novamente
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    // Método para inserir informações no banco de dados usando JdbcTemplate
    private static void insertLogToDatabase(String logEntry) {
        try {
            Conexao conexao = new Conexao();
            final JdbcTemplate con = conexao.getConexaoDoBanco();
            con.update("INSERT INTO log (dtCriacao, infos) VALUES (?, ?)", "INFO", logEntry);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized void logDiscoInfo(List<Disco> discos) {
        for (Disco discoT : discos) {
            logInfo("Disco Info: \n" + discoT.toString(), DiscoT.class);
        }
    }

    private static void checkLogRotation() throws IOException {
        long fileSize = new File(logFile).length();  // Verifica o tamanho do arquivo, se o tamanho do arquivo atingir o limite, faz a rotação
        /* A razão pela qual você multiplica por 1024 duas vezes
        (1 kilobyte = 1024 bytes e 1 megabyte = 1024 kilobytes)
        há 1024 bytes em um kilobyte e 1024 kilobytes em um megabyte.*/
        if (fileSize > tamanhoMaximo * 1024 * 1024) {
            rotateLogs();
        }
    }

    private static void rotateLogs() throws IOException {
        // Renomeia o arquivo atual com a dataAtual
        String rotatedFileName = logDir + dataFormatada + ".log";
        File currentLogFile = new File(logFile); //Cria um objeto File referente ao arquivo de log atual.
        File rotatedFile = new File(rotatedFileName); //Cria um novo objeto File com o nome do arquivo de log rotacionado.
        currentLogFile.renameTo(rotatedFile); //Renomeia o arquivo atual com o nome do arquivo rotacionado.

        // Cria um novo arquivo de log
        try (PrintWriter writer = new PrintWriter(new FileWriter(logFile, false))) {
            writer.println("Log rotated at " + dataFormatada);
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


    // calculo e registro das taxas de leitura e escrita dos discos além do espaço total.
    public static <T> void logSevere(String message, Class<T> clazz) {
        String logEntry = dataFormatada + "SEVERE: " + message + Logger.class;
        System.out.println(logEntry); // Imprime no console
        final String warningLogFile = "Component[SEVERE]" + dataFormatada + ".log";

        // Salva no arquivo de log
        try {
            checkLogRotation();
            try (PrintWriter writer = new PrintWriter(new FileWriter(warningLogFile, true))) {
                writer.println(logEntry);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <T> void logWarning(String message, Class<T> clazz) {
        String logEntry = dataFormatada + " [" + clazz.getSimpleName() + "] - " + message;
        System.out.println(logEntry);
        final String alertLogFile = "Component[WARNING]" + dataFormatada + ".log";
        // Salva no arquivo de log
        try {
            checkLogRotation();
            try (PrintWriter writer = new PrintWriter(new FileWriter(alertLogFile, true))) {
                writer.println(logEntry);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized <T> void logInfo(String message, Class<T> clazz) {
        String logEntry = dataFormatada + " [" + clazz.getSimpleName() + "] " + message;
        System.out.println(logEntry);
        // Salva no arquivo de log
        try {
            checkLogRotation();
            try (PrintWriter writer = new PrintWriter(new FileWriter("SystemComponent[INFO].log", true))) {
                writer.println(logEntry);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}