import com.github.britooo.looca.api.group.dispositivos.DispositivoUsb;
import com.github.britooo.looca.api.group.dispositivos.DispositivosUsbGrupo;

import java.util.List;
import java.util.Scanner;

public class Maquininha {
    private DispositivosUsbGrupo usbs;
    private Scanner in;
    private DispositivoUsb maquininha;

    public Maquininha(DispositivosUsbGrupo usbs, Scanner in) {
        this.usbs = usbs;
        this.in = in;
    }

//    public DispositivoUsb cadastrar() {
//
//        List<DispositivoUsb> comMaquininha = usbs.getDispositivosUsbConectados();
//
//        System.out.println("Por favor, garanta que sua maquininha de cartão esteja devidamente conectada. Quando estiver certo disso, pressione ENTER.");
//        String ok = in.nextLine();
//
//        System.out.println("Por favor Remova a maquininha. Após remover, pressione ENTER");
//        ok = in.nextLine();
//
//        List<DispositivoUsb> semMaquininha = usbs.getDispositivosUsbConectados();
//
//        for (DispositivoUsb dispositivo : comMaquininha) {
//            if (!semMaquininha.contains(dispositivo)) {
//                maquininha = dispositivo;
//            }
//        }
//        return maquininha;
//    }
//}
public DispositivoUsb cadastrar() {
    System.out.println("Por favor, garanta que sua maquininha de cartão esteja devidamente conectada. Quando estiver certo disso, pressione ENTER.");
    in.nextLine();

    System.out.println("Por favor, remova a maquininha. Após remover, pressione ENTER");
    in.nextLine();

    List<DispositivoUsb> dispositivosConectados = usbs.getDispositivosUsbConectados();

    for (DispositivoUsb dispositivo : dispositivosConectados) {
        if (!usbs.getDispositivosUsbConectados().contains(dispositivo)) {
            maquininha = dispositivo;
            break;
        }
    }

    return maquininha;
}
}
