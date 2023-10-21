import com.github.britooo.looca.api.group.dispositivos.DispositivoUsb;
import com.github.britooo.looca.api.group.dispositivos.DispositivosUsbGrupo;

public class UsbT {
    private String nome;
    private String idExclusivo;
    private Boolean conectado;

    public UsbT(String nome, String idExclusivo, Boolean conectado) {
        this.nome = nome;
        this.idExclusivo = idExclusivo;
        this.conectado = conectado;
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

    @Override
    public String toString() {
        return String.format("\nNome: %s\nId de dispositivo exclusivo: %s\nConectado: %s", this.getNome(), this.getIdExclusivo(),this.getConectado().toString());
    }
}
