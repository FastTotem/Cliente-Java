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
    public static void main(String[] args) {

        Scanner txtScanner = new Scanner(System.in);

        Totem totem = new Totem();
        String serialNumber = new SystemInfo().getHardware().getComputerSystem().getBaseboard().getSerialNumber();
        totem.setBoardSerialNumber(serialNumber);

        DiscosT discosT = new DiscosT();
        MaquinaT maquinaT = new MaquinaT();
        MemoriaT memoriaT = new MemoriaT();
        ProcessadorT processadorT = new ProcessadorT();
        DispositivosUsbGrupo usbs = new DispositivosUsbGrupo();
        Maquininha cadastroMaquina = new Maquininha(usbs);
        UsbT maquininha = new UsbT(usbs);

        Mensagens mensagem = new Mensagens();
        System.out.println(mensagem.getBoasVindas());

        totem = totem.validarTotemJaAtivo();
        Integer idTotem;

        if (totem == null) {

            Boolean chaveValida = false;
            totem = new Totem();

            do {
                System.out.println("Digite a chave de ativação do totem:");
                String chaveDeAcesso = txtScanner.nextLine();

                totem.setChaveDeAcesso(chaveDeAcesso);
                totem = totem.getTotem();
                if (totem == null) {
                    System.out.println("Chave de ativação incorreta!");
                    totem = new Totem();
                } else {
                    totem.inserirTotem();
                    chaveValida = true;
                }

            } while (!chaveValida);

            idTotem = totem.getIdTotem();

            // fkTotem para inserção na captura
            discosT.setFkTotem(idTotem);
            memoriaT.setFkTotem(idTotem);
            processadorT.setFkTotem(idTotem);
            maquinaT.setFkTotem(idTotem);

            // set id dos componentes para captura
            memoriaT.setIdComponente(memoriaT.inserirComponente());
            processadorT.setIdComponente(processadorT.inserirComponente());
            discosT.inserirDiscos();

            maquinaT.inserirDadosSistema();
            totem.setBoardSerialNumber(serialNumber);
            totem.inserirBoardSerialNumber();

            // Encontrando a maquininha
            maquininha.setMaquininha(cadastroMaquina.cadastrar());
            maquininha.setFkTotem(totem.getIdTotem());
            maquininha.inserirDispositivo();

        } else {

            idTotem = totem.getIdTotem();

            // fkTotem para inserção na captura
            discosT.setFkTotem(idTotem);
            memoriaT.setFkTotem(idTotem);
            processadorT.setFkTotem(idTotem);
            maquinaT.setFkTotem(idTotem);
            maquininha.setFkTotem(idTotem);

            // set id dos componentes para captura
            discosT.setIdDiscos();
            memoriaT.setIdComponenteTotemValidado();
            processadorT.setIdComponenteTotemValidado();
            maquininha.setIdComponenteTotemValidado();

            Logger logger = new Logger();

            new Thread(() -> {
                try {
                    while (true) {
                        List<DiscoT> discos = discosT.getDiscosT(); // Obtém a lista de discos
                        logger.logDiscoInfo(discos); // Chama o método passando a lista de discos
                        Thread.sleep(10000);// Aguarda 10 segundos antes de verificar novamente
                    }
                } catch (Exception e) {
                    Logger.logInfo("Erro Thread DISCO.\" " + e, Componente.class);
                    e.printStackTrace();
                }
            }).start();

            Timer timer = new Timer();
            long delay = 0;
            long interval = 24 * 60 * 60 * 1000;

            timer.scheduleAtFixedRate(new TimerTask() {
                public void run() {
                    FileUploader.enviarArquivoParaSlack(logger.getLogFile());
                }
            }, delay, interval);
        }

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

        scheduler.scheduleAtFixedRate(() -> {
            processadorT.monitorarUsoProcessador();
            memoriaT.monitorarUsoMemoria();
            maquinaT.monitorarTempoAtividade();
            maquininha.logUsbDevices();
            memoriaT.inserirCapturaUsoMemoria();
            processadorT.inserirCapturaUsoProcessador();
            discosT.inserirCapturasDisco();
            discosT.inserirReadWrite();
        }, 0, 1, TimeUnit.MINUTES);

        scheduler.scheduleAtFixedRate(() -> {
            maquinaT.inserirTempoDeAtividade();
            maquininha.verificarConexao();
        }, 0, 1, TimeUnit.HOURS);

        //execução contínua do código
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            scheduler.shutdown();
        }
    }
}