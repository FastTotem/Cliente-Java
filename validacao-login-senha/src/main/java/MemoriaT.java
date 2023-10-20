import com.github.britooo.looca.api.group.memoria.Memoria;
import com.github.britooo.looca.api.util.Conversor;

public class MemoriaT extends Componente{
    private Memoria memoria;
    private Integer idMemoria;
    private Long total;
    private Long disponivel;
    private Long emUso;
    private Double porcentagemUsada;

    public MemoriaT() {
        this.memoria = new Memoria();
    }

    public Long getPorcentagemUsada() {
        Long totalAtual = memoria.getTotal();
        Long emUsoAtual = memoria.getEmUso();
        Long porcentagem = emUsoAtual/totalAtual;
        return porcentagem;
    }

    public void inserirCapturaUsoMemoria(){

        emUso = memoria.getEmUso();
        this.inserirCapturaComponente(emUso, String.valueOf(TipoCapturaEnum.MEMORIA), idMemoria);
//        disponivel = memoria.getDisponivel();
//        this.inserirCapturaComponente(disponivel, String.valueOf(TipoCapturaEnum.MEMORIA));

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

    public void setTotal(Long total) {
        this.total = total;
    }

    public void setDisponivel(Long disponivel) {
        this.disponivel = disponivel;
    }

    public void setEmUso(Long emUso) {
        this.emUso = emUso;
    }

    public Integer getIdMemoria() {
        return idMemoria;
    }

    public void setIdMemoria(Integer idMemoria) {
        this.idMemoria = idMemoria;
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
