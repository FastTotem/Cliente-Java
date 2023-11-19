import com.github.britooo.looca.api.group.discos.DiscoGrupo;
import com.github.britooo.looca.api.group.memoria.Memoria;
import com.github.britooo.looca.api.group.processador.Processador;
import com.github.britooo.looca.api.group.sistema.Sistema;
import com.github.britooo.looca.api.util.Conversor;
import conexao.Conexao;
import org.springframework.jdbc.core.JdbcTemplate;
import oshi.hardware.HardwareAbstractionLayer;

import java.time.LocalDateTime;

public class MaquinaT {
    private Sistema sistema;
    private String sistemaOperacional;
    private String fabricante;
    private String nomeProcessador;
    private Long capacidadeRam;
    private Long capacidadeDisco;
    private Long tempoDeAtividade;
    private Integer fkTotem;
    private HardwareAbstractionLayer hal;

    private final Conexao conexao = new Conexao();
    private final JdbcTemplate con = conexao.getConexaoDoBanco();

    public MaquinaT() {
        this.sistema = new Sistema();
        Processador processador = new Processador();
        Memoria memoria = new Memoria();
        DiscoGrupo grupoDeDiscos = new DiscoGrupo();

        this.hal = new oshi.SystemInfo().getHardware();

        this.sistemaOperacional = sistema.getSistemaOperacional();
        this.fabricante = sistema.getFabricante();
        this.nomeProcessador = processador.getNome();
        this.capacidadeRam = memoria.getTotal();
        this.capacidadeDisco = grupoDeDiscos.getTamanhoTotal();
        this.tempoDeAtividade = sistema.getTempoDeAtividade();
    }

    public void inserirDadosSistema() {
        con.update("INSERT INTO infoMaquina " +
              "(sistemaOperacional, fabricante, nomeProcessador, " +
              "capacidadeRam, capacidadeDisco, fkTotem) " +
              "VALUES (?,?,?,?,?,?)", sistemaOperacional, fabricante, nomeProcessador, capacidadeRam, capacidadeDisco, fkTotem);

        System.out.println("Dados do sistema inseridos!");
    }

    public void inserirTempoDeAtividade() {
        con.update("INSERT INTO captura (valor, tipo, dataHora, fkTotem) VALUES (?,?,?,?)",
              tempoDeAtividade, String.valueOf(TipoEnum.TEMPO_ATIVIDADE), LocalDateTime.now(), fkTotem);

        System.out.println("Captura realizada!");
    }

    public Sistema getSistema() {
        return sistema;
    }

    public String getSistemaOperacional() {
        return getSistema().getSistemaOperacional();
    }

    public Integer getArquitetura() {
        return sistema.getArquitetura();
    }

    public Integer getFkTotem() {
        return fkTotem;
    }

    public void setFkTotem(Integer fkTotem) {
        this.fkTotem = fkTotem;
    }

    public void monitorarTempoAtividade() {
        while (true) {
            // Se o tempo de atividade atingir 80%, registra no log
            if (tempoDeAtividade >= 75.0) {
                Logger.logWarning("[ALERTA] Totem em muito tempo de atividade", MaquinaT.class);
            } else if (tempoDeAtividade >= 95.0) {
                Logger.logSEVERE("[SEVERO] É necessário Reiniciar o Totem ", MaquinaT.class);
            } else {
                Logger.logInfo("Tempo de atividade do Totem está ok!", MaquinaT.class);
                Logger.logInfo(toString(), MaquinaT.class);
            }
            Logger.logInfo(toString(), MaquinaT.class);
            // Adormece por um curto período antes de verificar novamente
            try {
                Thread.sleep(10000); // intervalo
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Sistema operacional: ").append(this.sistemaOperacional).append("\n");
//        sb.append("Fabricante: ").append(this.fabricante).append("\n");
//        sb.append("Arquitetura: ").append(this.arquitetura).append("bits\n");
//        sb.append("Inicializado: ").append(this.getInicializado()).append("\n");
        sb.append("Tempo de atividade: ").append(Conversor.formatarSegundosDecorridos(this.sistema.getTempoDeAtividade())).append("\n");
//        sb.append("Permissões: ").append("Executando como ").append(this.getPermissao() ? "root" : "usuário padrão").append("\n");
        return sb.toString();
    }
}
