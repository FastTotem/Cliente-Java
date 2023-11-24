import com.github.britooo.looca.api.group.processador.Processador;

public class ProcessadorT extends Componente {
    private final Processador processador;
    private final Integer numeroCores;

    public ProcessadorT() {
        this.processador = new Processador();
        this.tipoComponente = String.valueOf(TipoEnum.PROCESSADOR);
        this.numeroCores = processador.getNumeroCpusLogicas();
    }

    public void inserirCapturaUsoProcessador() {
        Double emUso = processador.getUso();
        inserirCapturaComponente(emUso, String.valueOf(TipoEnum.PROCESSADOR));
    }

    public void monitorarUsoProcessador() {
        while (true) {
            Double uso = processador.getUso();
            if (uso >= 80.0) {
                Logger.logInfo("⚠\uFE0F [ALERTA] Carga do sistema atingiu " + uso.shortValue() + "%", ProcessadorT.class);
            } else if (uso >= 98.0) {
                Logger.logInfo("❌ [SEVERO] Carga do sistema atingiu " + uso.shortValue() + "%", ProcessadorT.class);
            } else {
                Logger.logInfo("✅ [INFO] \n" + this, ProcessadorT.class);
            }
            try {
                Thread.sleep(1800000);
            } catch (InterruptedException e) {
                Logger.logInfo("Erro para monitorar uso do Processador.\" " + e, Componente.class);
                e.printStackTrace();
            }
        }
    }

    public String getModelo() {
        return processador.getNome();
    }

    public Integer getNumeroCors() {
        return numeroCores;
    }

    public Double getEmUso() {
        Double usoProcessador = processador.getUso();
        return (usoProcessador / 100) * 100;
    }

    public Double getFrequencia() {
        Long frequenciaEmHertz = processador.getFrequencia();
        return (frequenciaEmHertz / 1000000000.0);
    }

    public void setIdProcessadorTotemValidado(Integer idTotem) {
        idComponente = getIdComponente(String.valueOf(TipoEnum.PROCESSADOR), idTotem);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Fabricante: ").append(processador.getFabricante()).append("\n");
        sb.append("Modelo: ").append(getModelo()).append("\n");
        sb.append("Frequência: ").append(getFrequencia()).append(" GHz").append("\n");
        sb.append("Em Uso: ").append(Math.round(getEmUso())).append("%").append("\n");
        return sb.toString();
    }
}
