import com.github.britooo.looca.api.group.memoria.Memoria;
import com.github.britooo.looca.api.util.Conversor;

public class MemoriaT {
    private Memoria memoria;

    private Long total;
    private Long disponivel;
    private Long emUso;
    private Double porcentagemUsada;

    public MemoriaT() {
        this.memoria = new Memoria();
    }

    public Long getTotal() {
        return memoria.getTotal();
    }

    public Long getDisponivel() {
        return memoria.getDisponivel();
    }

    public Long getEmUso() {
        return memoria.getEmUso();
    }

    public Long getPorcentagemUsada() {
        Long totalAtual = memoria.getTotal();
        Long emUsoAtual = memoria.getEmUso();
        Long porcentagem = emUsoAtual/totalAtual;
        return porcentagem;
    }

    @Override
    public String toString(){
        StringBuilder sb = (new StringBuilder("Memoria")).append("\n");
        sb.append("Em uso: ").append(Conversor.formatarBytes(this.getEmUso())).append("\n");
        sb.append("Disponível: ").append(Conversor.formatarBytes(this.getDisponivel())).append("\n");
        sb.append("Total: ").append(Conversor.formatarBytes(this.getTotal())).append("\n");
        return sb.toString();
    }
}
