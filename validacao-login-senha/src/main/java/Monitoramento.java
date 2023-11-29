import com.github.britooo.looca.api.group.dispositivos.DispositivosUsbGrupo;
import com.sun.net.httpserver.HttpServer;
import funcoes.*;
import oshi.SystemInfo;
import slack.FileUploader;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Monitoramento {
    public static void main(String[] args) throws IOException {

        Logger.checkLogDirectory();

        int serverPort = 8080;
        InetAddress localHost = InetAddress.getLocalHost();

        HttpServer server = HttpServer.create(new InetSocketAddress(serverPort), 0);
        server.createContext("/inovacao/desligarTotem", new DesligarTotem());
        server.createContext("/inovacao/reiniciarTotem", new ReiniciarTotem());
        server.createContext("/inovacao/limparCache", new LimparCacheTotem());
        server.setExecutor(null);
        server.start();

        Scanner txtScanner = new Scanner(System.in);

        Totem totem = new Totem();
        String serialNumber = new SystemInfo().getHardware().getComputerSystem().getBaseboard().getSerialNumber();
        String enderecoIpTotem = localHost.getHostAddress() + ":" + serverPort;

        totem.setIpTotem(enderecoIpTotem);
        totem.setBoardSerialNumber(serialNumber);

        DiscosT discosT = new DiscosT();
        MaquinaT maquinaT = new MaquinaT();
        MemoriaT memoriaT = new MemoriaT();
        ProcessadorT processadorT = new ProcessadorT();
        DispositivosUsbGrupo usbs = new DispositivosUsbGrupo();
        Maquininha cadastroMaquina = new Maquininha(usbs);
        UsbT maquininha = new UsbT(usbs);

        Timer timer = new Timer();
        long delay = 0;
        long interval = 24 * 60 * 60 * 1000;

        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                FileUploader.enviarArquivoParaSlack(Logger.getLogFile());
            }
        }, delay, interval);

        Mensagens mensagem = new Mensagens();
        System.out.println(mensagem.getBoasVindas());

        totem = totem.validarTotemJaAtivo();
        Integer idTotem;
        Integer idEmpresa;

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
            idEmpresa = totem.getFkEmpresa();

            // fkTotem para inserção na captura
            discosT.setFkTotem(idTotem);
            memoriaT.setFkTotem(idTotem);
            processadorT.setFkTotem(idTotem);
            maquinaT.setFkTotem(idTotem);

            discosT.setFkEmpresa(idEmpresa);
            memoriaT.setFkEmpresa(idEmpresa);
            processadorT.setFkEmpresa(idEmpresa);
            maquininha.setFkEmpresa(idEmpresa);

            // set id dos componentes para captura
            memoriaT.setIdComponente(memoriaT.inserirComponente());
            processadorT.setIdComponente(processadorT.inserirComponente());
            discosT.inserirDiscos();

            maquinaT.inserirDadosSistema();
            totem.setBoardSerialNumber(serialNumber);
            totem.setIpTotem(enderecoIpTotem);
            totem.inserirBoardSerialNumber();
            totem.inserirIpTotem();

            // Encontrando a maquininha
            if (!serialNumber.equals("unknown")) {
                maquininha.setMaquininha(cadastroMaquina.cadastrar());
                maquininha.setFkTotem(totem.getIdTotem());
                maquininha.inserirDispositivo();
            }

        } else {

            idTotem = totem.getIdTotem();
            idEmpresa = totem.getFkEmpresa();

            // fkTotem para inserção na captura
            discosT.setFkTotem(idTotem);
            memoriaT.setFkTotem(idTotem);
            processadorT.setFkTotem(idTotem);
            maquinaT.setFkTotem(idTotem);
            maquininha.setFkTotem(idTotem);

            discosT.setFkEmpresa(idEmpresa);
            memoriaT.setFkEmpresa(idEmpresa);
            processadorT.setFkEmpresa(idEmpresa);
            maquininha.setFkEmpresa(idEmpresa);

            // set id dos componentes para captura
            discosT.setIdDiscos();
            memoriaT.setIdComponenteTotemValidado();
            processadorT.setIdComponenteTotemValidado();
            if (!serialNumber.equals("unknown")) {
                maquininha.setIdComponenteTotemValidado();
            }
        }

        totem.atualizarTotemAtivo();

        new Thread(processadorT::monitorarUsoProcessador).start();
        new Thread(memoriaT::monitorarUsoMemoria).start();
        new Thread(maquinaT::monitorarTempoAtividade).start();
        if (!serialNumber.equals("unknown")) {
            new Thread(maquininha::logUsbDevices).start();
        }

        new Thread(() -> {
            try {
                while (true) {
                    List<DiscoT> discos = discosT.getDiscosT(); // Obtém a lista de discos
                    Logger.logDiscoInfo(discos); // Chama o método passando a lista de discos
                    Thread.sleep(1800000); // Aguarda 2 minutos antes de verificar novamente
                }
            } catch (Exception e) {
                Logger.logInfo("Erro Thread DISCO.\" " + e, Componente.class);
                e.printStackTrace();
            }
        }).start();

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

        scheduler.scheduleAtFixedRate(() -> {
            memoriaT.inserirCapturaUsoMemoria();
            processadorT.inserirCapturaUsoProcessador();
            discosT.inserirPorcentagemArmazenada();
        }, 0, 1, TimeUnit.MINUTES);

        scheduler.scheduleAtFixedRate(() -> {
            maquinaT.inserirTempoDeAtividade();
            if (!serialNumber.equals("unknown")) {
                maquininha.verificarConexao();
            }
        }, 0, 1, TimeUnit.HOURS);

        //execução contínua do código
        CountDownLatch latch = new CountDownLatch(1);
        try {
            Thread.currentThread().join();
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            scheduler.shutdown();
        }
    }

}
