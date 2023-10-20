import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.discos.DiscoGrupo;
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

        Scanner in = new Scanner(System.in);
        Scanner txtScanner = new Scanner(System.in);
        Totem totem = new Totem();

        Mensagens mensagem = new Mensagens();
        System.out.println(mensagem.getBoasVindas());

        Boolean chaveValida = false;

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

        Componente componente = new Componente();
        componente.setFkTotem(totem.getIdTotem());
        MaquinaT maquinaT = new MaquinaT();
        MemoriaT memoriaT = new MemoriaT();
        ProcessadorT processadorT = new ProcessadorT();

        if (!totem.validarTotemJaAtivo()){
            memoriaT.setIdMemoria(componente.inserirComponentes(String.valueOf(TipoCapturaEnum.MEMORIA)));
            processadorT.setIdProcessador(componente.inserirComponentes(String.valueOf(TipoCapturaEnum.PROCESSADOR)));
            //inserir componentes restantes
            maquinaT.inserirDadosSistema(totem.getIdTotem());
            totem.setBoardSerialNumber((new SystemInfo()).getHardware().getComputerSystem().getBaseboard().getSerialNumber());
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
