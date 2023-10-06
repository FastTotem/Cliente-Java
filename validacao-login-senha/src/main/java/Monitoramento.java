import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.discos.Disco;
import com.github.britooo.looca.api.group.discos.DiscoGrupo;
import com.github.britooo.looca.api.group.dispositivos.DispositivoUsb;
import com.github.britooo.looca.api.group.dispositivos.DispositivosUsbGrupo;
import com.github.britooo.looca.api.group.memoria.Memoria;
import com.github.britooo.looca.api.group.processador.Processador;
import com.github.britooo.looca.api.group.sistema.Sistema;

import java.util.List;
import java.util.Scanner;


public class Monitoramento {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        Looca looca = new Looca();
        Mensagens mensagem = new Mensagens();
        Integer ligado = 1;
        Sistema sistema = looca.getSistema();


        Memoria memoria;
        Processador processador;
        DiscoGrupo grupoDeDiscos;
        DispositivosUsbGrupo grupoUsb;

        List<DispositivoUsb> dispositivoUsbs;
        List<Disco> discos;

        System.out.println(mensagem.getBoasVindas());

        // Provisorio
        Integer inputUser = 1;

        System.out.println(sistema);
        do {
            memoria = looca.getMemoria();
            processador = looca.getProcessador();
            grupoDeDiscos = looca.getGrupoDeDiscos();
            grupoUsb = looca.getDispositivosUsbGrupo();


            discos = grupoDeDiscos.getDiscos();
            dispositivoUsbs = grupoUsb.getDispositivosUsb();

            System.out.println("Memoria: ");
            System.out.println("Memoria total: " + memoria.getTotal());
            System.out.println("Memoria em uso: " + memoria.getEmUso());
            System.out.println("Memoria Disponivel: " + memoria.getDisponivel());

            System.out.println("\n");

            System.out.println("Processador: ");
            System.out.println("Nome: " + processador.getNome());
            System.out.println("Fabricante: " + processador.getFabricante());
            System.out.println("Microarquitetura: " + processador.getMicroarquitetura());
            System.out.println("Numero Cpus Logicas: " + processador.getNumeroCpusLogicas());
            System.out.println("Numero Cpus Fisicas: " + processador.getNumeroCpusFisicas());
            System.out.println("Numero Pacotes Físicos: " + processador.getNumeroPacotesFisicos());
            System.out.println("Frequencia: " + processador.getFrequencia());
            System.out.println("Uso: " + processador.getUso());

            System.out.println("\n");

            System.out.println("Grupo de Discos: ");
            System.out.println("Todos os discos: " + grupoDeDiscos.getDiscos());

            System.out.println("\n");

            System.out.println("Grupo de USB: ");
            System.out.println("Todos os discos: " + grupoUsb.getDispositivosUsb());

            System.out.println("\n");

            System.out.println("Insira 0 caso deseje sair: ");
            inputUser = in.nextInt();
            if (inputUser.equals(0)) ligado = 0;

        } while (ligado.equals(1));

        System.out.println(mensagem.getAdeus());
        System.exit(1);
    }
}
