import com.github.britooo.looca.api.group.dispositivos.DispositivosUsbGrupo;
import oshi.SystemInfo;

import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class Monitoramento {
    public static void main(String[] args) {

        Scanner txtScanner = new Scanner(System.in);

        Totem totem = new Totem();
        String serialNumber = new SystemInfo().getHardware().getComputerSystem().getBaseboard().getSerialNumber();
        totem.setBoardSerialNumber(serialNumber);

        Componente componente = new Componente();
        DiscosT discosT = new DiscosT();
        MaquinaT maquinaT = new MaquinaT();
        MemoriaT memoriaT = new MemoriaT();
        ProcessadorT processadorT = new ProcessadorT();

        DispositivosUsbGrupo usbs = new DispositivosUsbGrupo();
        Maquininha cadastroMaquina = new Maquininha(usbs, txtScanner);
        UsbT maquininha = new UsbT(usbs);

        Mensagens mensagem = new Mensagens();
        System.out.println(mensagem.getBoasVindas());

        totem = totem.validarTotemJaAtivo();

        if (totem == null){

            Boolean chaveValida = false;
            totem = new Totem();

            do {
                System.out.println("Digite a chave de ativação do totem:");
                String chaveDeAcesso = txtScanner.nextLine();

                totem.setChaveDeAcesso(chaveDeAcesso);
                totem = totem.getTotem();
                if (totem == null){
                    System.out.println("Chave de ativação incorreta!");
                    totem = new Totem();
                } else {
                    chaveValida = true;
                }

            } while (!chaveValida);

            // fkTotem para inserção na captura
            componente.setFkTotem(totem.getIdTotem());
            discosT.setFkTotem(totem.getIdTotem());
            memoriaT.setFkTotem(totem.getIdTotem());
            processadorT.setFkTotem(totem.getIdTotem());
            maquinaT.setFkTotem(totem.getIdTotem());

            // set id dos componentes para captura
            memoriaT.setIdMemoria(componente.inserirComponente(String.valueOf(TipoEnum.MEMORIA), null));
            processadorT.setIdProcessador(componente.inserirComponente(String.valueOf(TipoEnum.PROCESSADOR), null));
            discosT.inserirDiscos();

            maquinaT.inserirDadosSistema();
            totem.setBoardSerialNumber(serialNumber);
            totem.inserirBoardSerialNumber();

            // Encontrando a maquininha
            maquininha.setMaquininha(cadastroMaquina.cadastrar());
            maquininha.setFkTotem(totem.getIdTotem());
            maquininha.inserirDispositivo();

        } else {

            // fkTotem para inserção na captura
            discosT.setFkTotem(totem.getIdTotem());
            memoriaT.setFkTotem(totem.getIdTotem());
            processadorT.setFkTotem(totem.getIdTotem());
            maquinaT.setFkTotem(totem.getIdTotem());
            maquininha.setFkTotem(totem.getIdTotem());

            // set id dos componentes para captura
            discosT.setIdDiscos();
            memoriaT.setIdMemoriaTotemValidado(totem.getIdTotem());
            memoriaT.setFkTotem(totem.getIdTotem());
            processadorT.setIdProcessadorTotemValidado(totem.getIdTotem());

        }

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

        scheduler.scheduleAtFixedRate(() -> {
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
        CountDownLatch latch = new CountDownLatch(1);
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            scheduler.shutdown();
        }


    }

}
