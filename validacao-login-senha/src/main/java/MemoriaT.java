import com.github.britooo.looca.api.group.memoria.Memoria;
import com.github.britooo.looca.api.util.Conversor;

public class MemoriaT extends Componente {
    private Memoria memoria;
    private Long total;
    private Long disponivel;
    private Long emUso;
    private Double porcentagemUsada;

    public MemoriaT() {
        this.memoria = new Memoria();
        this.tipoComponente = String.valueOf(TipoEnum.MEMORIA);
    }

    public Double getPorcentagemEmUso() {
        Double totalAtual = Double.valueOf(memoria.getTotal());
        Double emUsoAtual = Double.valueOf(memoria.getEmUso());
        return (emUsoAtual * 100) / totalAtual;
    }

    public void inserirCapturaUsoMemoria() {
        inserirCapturaComponente(getPorcentagemEmUso(), String.valueOf(TipoEnum.MEMORIA));
    }

    public void monitorarUsoMemoria() {
        while (true) {
            Double porcentagemUso = getPorcentagemEmUso();
            if (porcentagemUso >= 80.0) {
                Logger.logWarning("⚠\uFE0F" + "[ALERTA] Memória atingiu " + porcentagemUso.shortValue() + "%", MemoriaT.class);
            } else if (porcentagemUso >= 99.0) {
                Logger.logWarning("❌" + "[SEVERO] Memória atingiu " + porcentagemUso.shortValue() + "%", MemoriaT.class);
            } else {
                Logger.logInfo("✅" + "[INFO] Memória: \n" + this, Logger.class);
            }
            try {
                Thread.sleep(1800000); // Aguarda 2 minutos antes de verificar novamente
            } catch (InterruptedException e) {
                Logger.logInfo("Erro para monitorar uso da Memória.\" " + e, Componente.class);
                e.printStackTrace();
            }
        }
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

    @Override
    public String toString() {
        return "Em uso: " + Conversor.formatarBytes(this.getEmUso()) + "\n" +
              "Disponível: " + Conversor.formatarBytes(this.getDisponivel()) + "\n" +
              "Total: " + Conversor.formatarBytes(this.getTotal()) + "\n";
    }

}
