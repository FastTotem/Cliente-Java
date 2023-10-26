import com.github.britooo.looca.api.group.dispositivos.DispositivoUsb;
import com.github.britooo.looca.api.group.dispositivos.DispositivosUsbGrupo;
import gui.TelaCadastroMaquininha;

import javax.swing.*;
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

    public DispositivoUsb cadastrar(){

        TelaCadastroMaquininha telaCadastroMaquininha = new TelaCadastroMaquininha();
        telaCadastroMaquininha.desenharTelaMaquininhaConectada(new TelaCadastroMaquininha.ActivationListener() {
            @Override
            public void onActivation() {
                telaCadastroMaquininha.fechar(telaCadastroMaquininha.getTelaChaveAtivacaoFrame());
            }
        });

        List<DispositivoUsb> comMaquininha = usbs.getDispositivosUsbConectados();

        telaCadastroMaquininha.desenharTelaMaquininhaDesconectada(new TelaCadastroMaquininha.ActivationListener() {
            @Override
            public void onActivation() {
                telaCadastroMaquininha.fechar(telaCadastroMaquininha.getTelaChaveAtivacaoFrame());
            }
        });

        List<DispositivoUsb> semMaquininha = usbs.getDispositivosUsbConectados();

        for (DispositivoUsb dispositivo : comMaquininha) {
            if (!semMaquininha.contains(dispositivo)) {
                maquininha = dispositivo;
            }
        }

        if (maquininha != null){
            JOptionPane.showMessageDialog(null, "Maquininha cadastrada com sucesso! Iniciando captura...", "Cadastro de maquininha", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Não foi possível encontrar a maquininha", "Erro - Maquininha não encontrada", JOptionPane.ERROR_MESSAGE);
        }

        return maquininha;
    }
}
