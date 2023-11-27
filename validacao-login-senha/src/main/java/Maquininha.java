import com.github.britooo.looca.api.group.dispositivos.DispositivoUsb;
import com.github.britooo.looca.api.group.dispositivos.DispositivosUsbGrupo;

import java.util.List;
import java.util.Scanner;

public class Maquininha {
    private DispositivosUsbGrupo usbs;
    private DispositivoUsb maquininha;

    public Maquininha(DispositivosUsbGrupo usbs, Scanner txtScanner) {
        this.usbs = usbs;
    }

    public DispositivoUsb cadastrar() {

        Scanner in = new Scanner(System.in);

        System.out.println("Por favor, garanta que sua maquininha de cartão esteja devidamente conectada. Quando estiver certo disso, pressione ENTER.");
        String ok = in.nextLine();

        List<DispositivoUsb> comMaquininha = usbs.getDispositivosUsbConectados();
  
        System.out.println("Por favor Remova a maquininha. Após remover, pressione ENTER");
        ok = in.nextLine();
        List<DispositivoUsb> semMaquininha = usbs.getDispositivosUsbConectados();

        for (DispositivoUsb dispositivo : comMaquininha) {
            if (!semMaquininha.contains(dispositivo)) {
                maquininha = dispositivo;
            }
        }
        return maquininha;
    }

}
