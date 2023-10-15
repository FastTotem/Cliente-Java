import com.github.britooo.looca.api.group.sistema.Sistema;
import com.github.britooo.looca.api.util.Conversor;

public class SistemaT {
    private Sistema sistema;
    private String so;
    private Integer arquitetura;
    private Long tempoDeAtividade;

    public SistemaT() {
        this.sistema = new Sistema();
        this.so = sistema.getSistemaOperacional();
        this.arquitetura = sistema.getArquitetura();
        this.tempoDeAtividade = sistema.getTempoDeAtividade();
    }

    public Sistema getSistema() {
        return sistema;
    }

    public String getSo() {
        return getSistema().getSistemaOperacional();
    }

    public Integer getArquitetura() {
        return sistema.getArquitetura();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Sistema operacional: ").append(this.so).append("\n");
//        sb.append("Fabricante: ").append(this.fabricante).append("\n");
        sb.append("Arquitetura: ").append(this.arquitetura).append("bits\n");
//        sb.append("Inicializado: ").append(this.getInicializado()).append("\n");
        sb.append("Tempo de atividade: ").append(Conversor.formatarSegundosDecorridos(this.sistema.getTempoDeAtividade())).append("\n");
//        sb.append("Permissões: ").append("Executando como ").append(this.getPermissao() ? "root" : "usuário padrão").append("\n");
        return sb.toString();
    }
}
