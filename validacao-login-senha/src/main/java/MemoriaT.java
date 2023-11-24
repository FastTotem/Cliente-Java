import com.github.britooo.looca.api.group.memoria.Memoria;
import com.github.britooo.looca.api.util.Conversor;
import oshi.hardware.HardwareAbstractionLayer;

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
        Double usoMemoriaPorcentagem = (emUsoAtual * 100) / totalAtual;
        return usoMemoriaPorcentagem;
    }

    public void inserirCapturaUsoMemoria() {
        this.inserirCapturaComponente(getPorcentagemEmUso(), String.valueOf(TipoEnum.MEMORIA));
//        disponivel = memoria.getDisponivel();
//        this.inserirCapturaComponente(disponivel, String.valueOf(TipoCapturaEnum.MEMORIA));
    }

    public void monitorarUsoMemoria() {
        MemoriaT memoriaT = new MemoriaT();
        Logger.logInfo(toString(), MemoriaT.class);
        while (true) {
            // Se a memoria atingir 80% ou mais, registra no log
            if (memoriaT.getPorcentagemEmUso() >= 80.0) {
                Logger.logInfo("⚠\uFE0F" + "[ALERTA] Memória atingiu " + getPorcentagemEmUso().shortValue() + "%" , MemoriaT.class);
            } else if (memoriaT.getPorcentagemEmUso() >= 99.0) {
                Logger.logInfo("❌" + "[SEVERO] Memória atingiu " + getPorcentagemEmUso().shortValue() + "%", MemoriaT.class);
            } else {
                Logger.logInfo("✅" + "[INFO] Memoria: \n" + this, Logger.class);
            }
            // Adormece por um curto período antes de verificar novamente
            try {
                Thread.sleep(1800000);// Aguarda 2 minutos antes de verificar novamente
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

    public void setIdMemoriaTotemValidado() {
        idComponente = getIdComponente(String.valueOf(TipoEnum.MEMORIA), fkTotem);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Em uso: ").append(Conversor.formatarBytes(this.getEmUso())).append("\n");
        sb.append("Disponível: ").append(Conversor.formatarBytes(this.getDisponivel())).append("\n");
        sb.append("Total: ").append(Conversor.formatarBytes(this.getTotal())).append("\n");
        return sb.toString();
    }
}
