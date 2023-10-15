import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.discos.Disco;
import com.github.britooo.looca.api.group.discos.DiscoGrupo;
import com.github.britooo.looca.api.group.dispositivos.DispositivoUsb;
import com.github.britooo.looca.api.group.dispositivos.DispositivosUsbGrupo;
import com.github.britooo.looca.api.group.memoria.Memoria;
import com.github.britooo.looca.api.group.processador.Processador;
import com.github.britooo.looca.api.group.sistema.Sistema;
import org.w3c.dom.ls.LSOutput;

import javax.swing.*;
import java.util.List;
import java.util.Scanner;


public class Monitoramento {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        Looca looca = new Looca();
        Mensagens mensagem = new Mensagens();
        Integer ligado = 1;


        MemoriaT memoriaTotem = new MemoriaT();
        ProcessadorT processadorTotem = new ProcessadorT();
        SistemaT sistemaTotem = new SistemaT();
        DiscoT discoTotem = new DiscoT();
        DiscoGrupo grupoDeDiscos = new DiscoGrupo();

        DispositivosUsbGrupo grupoUsb;
        List<DispositivoUsb> dispositivoUsbs;

        System.out.println(mensagem.getBoasVindas());

        // Provisorio
        Integer inputUser = 1;

        System.out.println(sistemaTotem);
        do {
            grupoUsb = looca.getDispositivosUsbGrupo();
            dispositivoUsbs = grupoUsb.getDispositivosUsb();

            System.out.println(memoriaTotem);
            System.out.println("\n");

            System.out.println(processadorTotem);
            System.out.println("\n");

            System.out.println(discoTotem);
            System.out.format("""
                  Todos os discos: %s,
                  Quantidade de Volumes no disco: %s,
                  Tamanho total do Disco: %s,
                  """, grupoDeDiscos.getQuantidadeDeDiscos(),grupoDeDiscos.getQuantidadeDeVolumes(),grupoDeDiscos.getTamanhoTotal().shortValue());

            System.out.format("""
              Grupo de USB: %s,
              Todos os USBs: %s,
              USBs em USO: %s,
              """, grupoUsb.getDispositivosUsbConectados(), grupoUsb.getDispositivosUsb(), grupoUsb.getDispositivosUsbConectados());

            System.out.println("\nInsira 0 caso deseje sair: ");
            inputUser = in.nextInt();
            if (inputUser.equals(0)) ligado = 0;

        } while (ligado.equals(1));

        System.out.println(mensagem.getAdeus());
        System.exit(1);
    }

}
