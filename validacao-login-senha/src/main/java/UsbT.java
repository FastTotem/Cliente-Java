import com.github.britooo.looca.api.group.dispositivos.DispositivoUsb;
import com.github.britooo.looca.api.group.dispositivos.DispositivosUsbGrupo;

import java.util.List;

public class UsbT {
    private String nome;
    private String idExclusivo;
    private DispositivoUsb maquininha;
    private DispositivosUsbGrupo usbs;
    private Boolean conectado;

    public UsbT(DispositivoUsb maquininha, DispositivosUsbGrupo usbs) {
        this.maquininha = maquininha;
        this.nome = maquininha.getNome();
        this.idExclusivo = maquininha.getIdDispositivoUsbExclusivo();
        this.usbs = usbs;
    }

    public String getNome() {
        return nome;
    }

    public String getIdExclusivo() {
        return idExclusivo;
    }

    public Boolean getConectado() {
        return conectado;
    }

    public Boolean verificarConexao(){
        List<DispositivoUsb> usbsConectados = usbs.getDispositivosUsbConectados();
        if (usbsConectados.contains(maquininha)){
            return true;
        } else{
            return false;
        }
    }

    @Override
    public String toString() {
        return String.format("\nNome: %s\nId de dispositivo exclusivo: %s\nConectado: %s", this.getNome(), this.getIdExclusivo(),this.getConectado().toString());
    }
}
