import com.github.britooo.looca.api.group.dispositivos.DispositivoUsb;
import com.github.britooo.looca.api.group.dispositivos.DispositivosUsbGrupo;

import java.util.List;

public class UsbT extends Componente {
    private String nome;
    private String idExclusivo;
    private DispositivoUsb maquininha;
    private DispositivosUsbGrupo usbs;
    private Integer idUsb;

    public UsbT(DispositivoUsb maquininha, DispositivosUsbGrupo usbs) {
        this.maquininha = maquininha;
        this.nome = maquininha.getNome();
        this.idExclusivo = maquininha.getIdDispositivoUsbExclusivo();
        this.usbs = usbs;
    }

    public void verificarConexao(){
        List<DispositivoUsb> usbsConectados = usbs.getDispositivosUsbConectados();
        if (usbsConectados.contains(maquininha)){
            inserirCapturaComponente(1.0, String.valueOf(TipoEnum.USB), idUsb);
        } else{
            inserirCapturaComponente(0.0, String.valueOf(TipoEnum.USB), idUsb);
        }
    }

    public void inserirDispositivo(){
        idUsb = inserirComponente(String.valueOf(TipoEnum.USB), nome);
    }

    public String getNome() {
        return nome;
    }

    public String getIdExclusivo() {
        return idExclusivo;
    }

    public Integer getIdUsb() {
        return idUsb;
    }

    public void setIdUsb(Integer idUsb) {
        this.idUsb = idUsb;
    }

    @Override
    public String toString() {
        return String.format("\nNome: %s\nId de dispositivo exclusivo: %s\nConectado: %s", this.getNome(), this.getIdExclusivo().toString());
    }
}
