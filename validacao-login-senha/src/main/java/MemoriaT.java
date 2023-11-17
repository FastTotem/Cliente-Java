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
        this.tipoComponente = String.valueOf(TipoEnum.MEMORIA);
    }

    public Double getPorcentagemUsada() {
        Double totalAtual = Double.valueOf(memoria.getTotal());
        Double emUsoAtual = Double.valueOf(memoria.getEmUso());

        return (emUsoAtual/totalAtual) * 100;
    }

    public void inserirCapturaUsoMemoria(){
        this.inserirCapturaComponente(getPorcentagemUsada(), String.valueOf(TipoEnum.MEMORIA), idMemoria);
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

    public void setIdMemoriaTotemValidado(Integer idTotem) {
        idMemoria = getIdComponente(String.valueOf(TipoEnum.MEMORIA), idTotem);
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
