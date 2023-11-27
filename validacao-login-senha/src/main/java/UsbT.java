import com.github.britooo.looca.api.group.dispositivos.DispositivoUsb;
import com.github.britooo.looca.api.group.dispositivos.DispositivosUsbGrupo;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class UsbT extends Componente {
    private String nome;
    private String idExclusivo;
    private DispositivoUsb maquininha;
    private DispositivosUsbGrupo usbs;
    private JdbcTemplate jdbcTemplate; // Adicione o JdbcTemplate como um membro da classe

    public UsbT(DispositivosUsbGrupo usbs) {
        this.usbs = usbs;
        this.tipoComponente = String.valueOf(TipoEnum.USB);
    }

    public UsbT(DispositivoUsb maquininha, DispositivosUsbGrupo usbs) {
        this.maquininha = maquininha;
        this.nome = maquininha.getNome();
        this.idExclusivo = maquininha.getIdDispositivoUsbExclusivo();
        this.usbs = usbs;
        this.tipoComponente = String.valueOf(TipoEnum.USB);
    }

    public void verificarConexao() {
        List<DispositivoUsb> usbsConectados = usbs.getDispositivosUsbConectados();
        idExclusivo = searchNomeComponente();
        for (DispositivoUsb usb : usbsConectados) {
            if (usb.getIdDispositivoUsbExclusivo().equals(idExclusivo)) {
                maquininha = usb;
            }
        }
        if (usbsConectados.contains(maquininha)) {
            inserirCapturaComponente(1.0, String.valueOf(TipoEnum.USB));
        } else {
            inserirCapturaComponente(0.0, String.valueOf(TipoEnum.USB));
        }
    }

    public void logUsbDevices() {
        String usbInfo = "Dispositivos USB:\n";
        usbInfo += "Dispositivos Conectados: " + usbs.getDispositivosUsbConectados() + "\n";
        usbInfo += "Total de Dispositivos USBs: " + usbs.getTotalDispositvosUsb() + "\n";
        Logger.logInfo(usbInfo, Logger.class);
        if (usbs.getDispositivosUsbConectados() == null) {
            Logger.logWarning("⚠️ [ALERTA] Nenhum USB Conectado", UsbT.class);
        }
    }

    public void inserirDispositivo() {
        idExclusivo = maquininha.getIdDispositivoUsbExclusivo();
        nomeComponente = idExclusivo;
        idComponente = inserirComponente();
    }

    public String getNome() {
        return nome;
    }

    public String getIdExclusivo() {
        if (jdbcTemplate == null) {
            throw new IllegalStateException("JdbcTemplate não foi configurado corretamente");
        }
        return idExclusivo;
    }

    public void setMaquininha(DispositivoUsb maquininha) {
        this.maquininha = maquininha;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public String toString() {
        return String.format("\nNome: %s\nId de dispositivo exclusivo: %s\nConectado: %s", this.getNome(), this.getIdExclusivo().toString());
    }

    public void setIdUsbTotemValidado() { idExclusivo = String.valueOf(getIdComponente(String.valueOf(TipoEnum.USB), fkTotem));
    }
}
