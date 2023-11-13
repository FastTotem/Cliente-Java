import com.github.britooo.looca.api.group.memoria.Memoria;
import com.github.britooo.looca.api.util.Conversor;
import oshi.hardware.*;

public class MemoriaT extends Componente{
    private Memoria memoria;
    private Integer idMemoria;
    private Long total;
    private Long disponivel;
    private Long emUso;
    private Double porcentagemUsada;
    private HardwareAbstractionLayer hal;

    public MemoriaT() {
        this.memoria = new Memoria();
        this.hal = new oshi.SystemInfo().getHardware();
    }

    public Long getPorcentagemUsada() {
        Long totalAtual = memoria.getTotal();
        Long emUsoAtual = memoria.getEmUso();
        Long porcentagem = emUsoAtual/totalAtual;
        return porcentagem;
    }

    public void inserirCapturaUsoMemoria(){
        emUso = memoria.getEmUso();
        this.inserirCapturaComponente(emUso, String.valueOf(TipoEnum.MEMORIA), idMemoria);
//        disponivel = memoria.getDisponivel();
//        this.inserirCapturaComponente(disponivel, String.valueOf(TipoCapturaEnum.MEMORIA));
    }

    public void monitorarUsoProcessador() {
        GlobalMemory memoriaG = hal.getMemory();
        Logger.logInfo(toString(), MemoriaT.class, "Informações relevantes para o Contexto");
        while (true) {
            long systemLoadAverage = memoriaG.getTotal();

            // Se a memoria atingir 80% ou mais, registra no log
            if (memoria.getEmUso() >= 80.0) {
                Logger.logWarning("Memória atingiu " + memoria.getEmUso().shortValue()+ "%");
                notificarAdministrador("Memória atingiu " + memoria.getEmUso().shortValue()+ "% de sua utilização");
            }

            // Adormece por um curto período antes de verificar novamente
            try {
                Thread.sleep(3000); // Ajuste o intervalo conforme necessário
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    private void log(String s) {}
    private void notificarAdministrador(String mensagem) {
        // Isso pode ser feito por slack.
        System.out.println("Notificação para administrador: " + mensagem);
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
