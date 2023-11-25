import com.github.britooo.looca.api.group.dispositivos.DispositivosUsbGrupo;
import oshi.SystemInfo;
import slack.FileUploader;

import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Monitoramento {
    private static Integer idTotem;
    public static void main(String[] args) {


        Scanner txtScanner = new Scanner(System.in);
        Logger logger = new Logger();
        DiscosT discosT = new DiscosT();
        MaquinaT maquinaT = new MaquinaT();
        MemoriaT memoriaT = new MemoriaT();
        ProcessadorT processadorT = new ProcessadorT();
        DispositivosUsbGrupo usbs = new DispositivosUsbGrupo();
        Maquininha cadastroMaquina = new Maquininha(usbs, txtScanner);
        UsbT maquininha = new UsbT(usbs);
        UsbT usbT = new UsbT(usbs);

        Timer timer = new Timer();
        long delay = 0;
        long interval = 24 * 60 * 60 * 1000;

        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                FileUploader.enviarArquivoParaSlack(logger.getLogFile());
            }
        }, delay, interval);

        Totem totem = configurarTotem(txtScanner);
        if (totem == null) {
            totem = new Totem();
            configurarNovoTotem(totem, txtScanner);
            idTotem = totem.getIdTotem();
        } else {
            idTotem = totem.getIdTotem();
            configurarTotemExistente(totem, discosT, memoriaT, processadorT, maquinaT, maquininha);
        }
    }
    private static Totem configurarTotem(Scanner txtScanner) {
        Totem totem = new Totem();
        String serialNumber = new SystemInfo().getHardware().getComputerSystem().getBaseboard().getSerialNumber();
        totem.setBoardSerialNumber(serialNumber);

        Mensagens mensagem = new Mensagens();
        System.out.println(mensagem.getBoasVindas());

        return totem.validarTotemJaAtivo();
    }

    private static void configurarNovoTotem(Totem totem, Scanner txtScanner) {
        boolean chaveValida = false;

        do {
            System.out.println("Digite a chave de ativação do totem:");
            String chaveDeAcesso = txtScanner.nextLine();

            totem.setChaveDeAcesso(chaveDeAcesso);
            totem = totem.getTotem();
            if (totem == null) {
                System.out.println("Chave de ativação incorreta!");
                totem = new Totem();
            } else {
                chaveValida = true;
            }

        } while (!chaveValida);
    }

    private static void configurarTotemExistente(Totem totem, DiscosT discosT, MemoriaT memoriaT, ProcessadorT processadorT, MaquinaT maquinaT, UsbT maquininha) {
        Integer idTotem = totem.getIdTotem();

        discosT.setFkTotem(idTotem);
        memoriaT.setFkTotem(idTotem);
        processadorT.setFkTotem(idTotem);
        maquinaT.setFkTotem(idTotem);
        maquininha.setFkTotem(idTotem);

        discosT.setIdDiscos();
        memoriaT.setIdMemoriaTotemValidado();
        processadorT.setIdProcessadorTotemValidado(idTotem);
        maquininha.setIdUsbTotemValidado();

        iniciarThreadsDeMonitoramento(processadorT, memoriaT, maquinaT, discosT, maquininha);
        configurarAgendamentoDeTarefas(processadorT, memoriaT, maquinaT, maquininha);
    }

    private static void iniciarThreadsDeMonitoramento(ProcessadorT processadorT, MemoriaT memoriaT, MaquinaT maquinaT, DiscosT discosT, UsbT maquininha) {
        // Inicia threads separadas para monitorar cada componente
        new Thread(processadorT::monitorarUsoProcessador).start();
        new Thread(memoriaT::monitorarUsoMemoria).start();
        new Thread(maquinaT::monitorarTempoAtividade).start();
        new Thread(maquininha::logUsbDevices).start();
        new Thread(() -> {
            try {
                while (true) {
                    List<DiscoT> discos = discosT.getDiscosT(); // Obtém a lista de discos
                    Logger.logDiscoInfo(discos); // Chama o método passando a lista de discos
                    Thread.sleep(1800000);// Aguarda 2 minutos antes de verificar novamente
                }
            } catch (Exception e) {
                Logger.logInfo("Erro Thread DISCO.\" " + e, Componente.class);
                e.printStackTrace();
            }
        }).start();
    }

    private static void configurarAgendamentoDeTarefas(ProcessadorT processadorT, MemoriaT memoriaT, MaquinaT maquinaT, UsbT maquininha) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

        scheduler.scheduleAtFixedRate(() -> {
            processadorT.monitorarUsoProcessador();
            memoriaT.monitorarUsoMemoria();
            maquinaT.monitorarTempoAtividade();
            maquininha.logUsbDevices();
            memoriaT.inserirCapturaUsoMemoria();
            processadorT.inserirCapturaUsoProcessador();
        }, 0, 1, TimeUnit.MINUTES);

        scheduler.scheduleAtFixedRate(() -> {
            maquinaT.inserirTempoDeAtividade();
            maquininha.verificarConexao();
        }, 0, 1, TimeUnit.HOURS);
    }

}