import com.github.britooo.looca.api.group.memoria.Memoria;
import com.github.britooo.looca.api.util.Conversor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;

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

    public Double getPorcentagemEmUso() {
        Double totalAtual = Double.valueOf(memoria.getTotal());
        Double emUsoAtual = Double.valueOf(memoria.getEmUso());
        Double usoMemoriaPorcentagem = (emUsoAtual * 100) / totalAtual;
        return usoMemoriaPorcentagem;
    }

    public void inserirCapturaUsoMemoria(){
        this.inserirCapturaComponente(getPorcentagemEmUso(), String.valueOf(TipoEnum.MEMORIA), idMemoria);
//        disponivel = memoria.getDisponivel();
//        this.inserirCapturaComponente(disponivel, String.valueOf(TipoCapturaEnum.MEMORIA));
}

    public void monitorarUsoMemoria() {
        GlobalMemory memoriaG = hal.getMemory();
        Logger.logInfo(toString(), MemoriaT.class);
        while (true) {
            String memoriaInfo = "Memoria Info:\n";
            memoriaInfo += "Total: " + memoria.getTotal() + "\n";
            memoriaInfo += "Usado: " + memoria.getEmUso() + "\n";
            memoriaInfo += "Disponível : " + memoria.getDisponivel() + "\n";

            long systemLoadAverage = memoriaG.getTotal();
            // Se a memoria atingir 80% ou mais, registra no log
            if (memoria.getEmUso() >= 80.0) {
                Logger.logWarning("[ALERTA] Memória atingiu " + getPorcentagemEmUso().shortValue() + "%", MemoriaT.class);
            } else if (memoria.getEmUso() >= 99.0) {
                Logger.logSevere("[SEVERO] Memória atingiu " + getPorcentagemEmUso().shortValue() + "%", MemoriaT.class);
            } else {
               Logger.logInfo(memoriaInfo, Logger.class);
            }
            // Adormece por um curto período antes de verificar novamente
            try {
                Thread.sleep(10000); // Ajuste o intervalo conforme necessário
            } catch (InterruptedException e) {
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
