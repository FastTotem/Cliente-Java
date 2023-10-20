import com.github.britooo.looca.api.group.discos.Disco;
import com.github.britooo.looca.api.group.discos.DiscoGrupo;
import com.github.britooo.looca.api.group.memoria.Memoria;
import com.github.britooo.looca.api.group.processador.Processador;
import com.github.britooo.looca.api.group.sistema.Sistema;
import com.github.britooo.looca.api.util.Conversor;
import conexao.Conexao;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;

public class MaquinaT {
    private Sistema sistema;
    private String sistemaOperacional;
    private String fabricante;
    private String nomeProcessador;
    private Long capacidadeRam;
    private Long capacidadeDisco;
    private Long tempoDeAtividade;

    private final Conexao conexao = new Conexao();
    private final JdbcTemplate con = conexao.getConexaoDoBanco();

    public MaquinaT() {
        this.sistema = new Sistema();
        Processador processador = new Processador();
        Memoria memoria = new Memoria();
        DiscoGrupo grupoDeDiscos = new DiscoGrupo();

        this.sistemaOperacional = sistema.getSistemaOperacional();
        this.fabricante = sistema.getFabricante();
        this.nomeProcessador = processador.getNome();
        this.capacidadeRam = memoria.getTotal();
        this.capacidadeDisco = grupoDeDiscos.getTamanhoTotal();
        this.tempoDeAtividade = sistema.getTempoDeAtividade();
    }

    public void inserirDadosSistema(Integer idTotem){
        con.update("INSERT INTO infoMaquina " +
                "(sistemaOperacional, fabricante, nomeProcessador, " +
                "capacidadeRam, capacidadeDisco, fkTotem) " +
                "VALUES (?,?,?,?,?,?)", sistemaOperacional, fabricante, nomeProcessador, capacidadeRam, capacidadeDisco, idTotem);

    }

    public void inserirTempoDeAtividade(){
        con.update("INSERT INTO captura (valor, tipo, dataHora) VALUES (?,?,?)",
                tempoDeAtividade, String.valueOf(TipoCapturaEnum.SISTEMA), LocalDateTime.now());

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
