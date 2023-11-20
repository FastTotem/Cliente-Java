import com.github.britooo.looca.api.group.dispositivos.DispositivoUsb;
import com.github.britooo.looca.api.group.dispositivos.DispositivosUsbGrupo;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class UsbT extends Componente {
    private String nome;
    private String idExclusivo;
    private DispositivoUsb maquininha;
    private DispositivosUsbGrupo usbs;
    private Integer idUsb;
    private JdbcTemplate jdbcTemplate; // Adicione o JdbcTemplate como um membro da classe

    public UsbT() {
    }

    public UsbT(DispositivosUsbGrupo usbs) {
        this.usbs = usbs;
    }

    public UsbT(DispositivoUsb maquininha, DispositivosUsbGrupo usbs) {
        this.maquininha = maquininha;
        this.nome = maquininha.getNome();
        this.idExclusivo = maquininha.getIdDispositivoUsbExclusivo();
        this.usbs = usbs;
    }

    public void verificarConexao() {
        List<DispositivoUsb> usbsConectados = usbs.getDispositivosUsbConectados();
        idExclusivo = getNomeComponente(String.valueOf(TipoEnum.USB));
        idUsb = getIdComponente(String.valueOf(TipoEnum.USB), getFkTotem());
        for (DispositivoUsb usb : usbsConectados) {
            if (usb.getIdDispositivoUsbExclusivo().equals(idExclusivo)) {
                maquininha = usb;
            }
        }
        if (usbsConectados.contains(maquininha)){
            inserirCapturaComponente(1.0, String.valueOf(TipoEnum.USB));
        } else{
            inserirCapturaComponente(0.0, String.valueOf(TipoEnum.USB));
        }
    }
    public void logUsbDevices() {
        String usbInfo = "Dispositivos USB:\n";
        usbInfo += "Dispositivos Conectados: " + usbs.getDispositivosUsbConectados() + "\n";
        usbInfo += "Total de Dispositivos USBs: " + usbs.getTotalDispositvosUsb() + "\n";
        Logger.logInfo(usbInfo, Logger.class);
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
        return idExclusivo;
    }

    public Integer getIdUsb() {
        return idUsb;
    }

    public void setIdUsb(Integer idUsb) {
        this.idUsb = idUsb;
    }

    public void setIdUsbTotemValidado() {
        idUsb = getIdComponente(String.valueOf(TipoEnum.USB), fkTotem);
    }

    public void setMaquininha(DispositivoUsb maquininha) {
        this.maquininha = maquininha;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String getNomeComponente(String tipoComponente) {
        String nomeComponente; // Valor padrão caso não seja encontrado nenhum componente

        // Verifica se o jdbcTemplate foi configurado antes de fazer a consulta
        if (jdbcTemplate == null) {
            throw new IllegalStateException("JdbcTemplate não foi configurado corretamente");
        }

        try {
            // consulta para obter o nome do componente com base no tipoComponente
            nomeComponente = jdbcTemplate.queryForObject("SELECT nomeComponente FROM componente WHERE tipoComponente = ?", String.class, tipoComponente);
        } catch (EmptyResultDataAccessException e) {
            // Se resultado vazio definir uma mensagem de log ou lançar uma exceção
            Logger.logInfo("Componente não encontrado", UsbT.class); // Valor padrão ou mensagem de erro
            throw new RuntimeException("Componente não encontrado para o tipo: " + tipoComponente);
        }
        return nomeComponente;
    }

    public Integer getIdComponente(String tipoComponente, Integer fkTotem) {
        Integer idComponente = null;

        try {
            idComponente = jdbcTemplate.queryForObject("SELECT idComponente FROM componente WHERE tipoComponente = ? AND fkTotem = ?", Integer.class, tipoComponente, fkTotem);
        } catch (EmptyResultDataAccessException e) {
            Logger.logInfo("Componente não encontrado", UsbT.class);
            throw new RuntimeException("Componente não encontrado para o tipo: " + tipoComponente);
        }

        return idComponente;
    }

    @Override
    public String toString() {
        return String.format("\nNome: %s\nId de dispositivo exclusivo: %s\nConectado: %s", this.getNome(), this.getIdExclusivo().toString());
    }
}
