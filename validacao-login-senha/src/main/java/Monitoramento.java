import com.github.britooo.looca.api.group.dispositivos.DispositivoUsb;
import com.github.britooo.looca.api.group.dispositivos.DispositivosUsbGrupo;
import oshi.SystemInfo;

import java.util.List;
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

        MaquinaT maquinaT = new MaquinaT();
        MemoriaT memoriaT = new MemoriaT();
        ProcessadorT processadorT = new ProcessadorT();

        DispositivosUsbGrupo usbs = new DispositivosUsbGrupo();

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

            componente.setFkTotem(totem.getIdTotem());

            memoriaT.setIdMemoria(componente.inserirComponentes(String.valueOf(TipoCapturaEnum.MEMORIA)));
            processadorT.setIdProcessador(componente.inserirComponentes(String.valueOf(TipoCapturaEnum.PROCESSADOR)));
            //inserir componentes restantes

            maquinaT.inserirDadosSistema(totem.getIdTotem());
            totem.setBoardSerialNumber(serialNumber);
            totem.inserirBoardSerialNumber();

            // Encontrando a maquininha
            Maquininha cadastroMaquina = new Maquininha(usbs, txtScanner);
            UsbT maquininha = new UsbT(cadastroMaquina.cadastrar(),usbs);

        } else {
            memoriaT.setIdMemoriaTotemValidado(totem.getIdTotem());
            processadorT.setIdProcessadorTotemValidado(totem.getIdTotem());
        }

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

        scheduler.scheduleAtFixedRate(() -> {
            memoriaT.inserirCapturaUsoMemoria();
            processadorT.inserirCapturaUsoProcessador();
        }, 0, 1, TimeUnit.MINUTES);

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
