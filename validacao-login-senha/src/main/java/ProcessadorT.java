import com.github.britooo.looca.api.group.processador.Processador;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;

public class ProcessadorT extends Componente {
    private Processador processador;
    private Integer idProcessador;
    private Double emUso;
    private Long frequencia;
    private HardwareAbstractionLayer hal;

    public ProcessadorT() {
        this.hal = new oshi.SystemInfo().getHardware();
        this.processador = new Processador();
    }

    public void inserirCapturaUsoProcessador() {
        Double emUso = processador.getUso();
//        frequencia = processador.getFrequencia();
        inserirCapturaComponente(emUso, String.valueOf(TipoEnum.PROCESSADOR), idProcessador);
//        inserirCapturaComponente(frequencia, String.valueOf(TipoCapturaEnum.PROCESSADOR));
    }

    public void monitorarUsoProcessador() {
        CentralProcessor processor = hal.getProcessor();
        while (true) {
            double[] systemLoadAverage = processor.getSystemLoadAverage(3); // 3 indica a média dos últimos 3 segundos - aceita de 1 a 3
            // Se a carga do sistema atingir 80%, registra no log
            if (processador.getUso() >= 80.0) {
                Logger.logWarning("Carga do sistema atingiu " + processador.getUso().shortValue() + "%");
                notificarAdministrador("Carga do sistema atingiu " + processador.getUso().shortValue() + "%");
            } else if (processador.getUso() >= 99.0) {
                Logger.logSEVERE("Carga do sistema atingiu " + processador.getUso().shortValue() + "%");
                notificarAdministrador("Carga do sistema atingiu " + processador.getUso().shortValue() + "%");
            } else {
                Logger.logInfo("Carga do sistema está ok!", ProcessadorT.class, "Informações relevantes para o Contexto");
                Logger.logInfo(toString(), ProcessadorT.class, "Informações relevantes para o Contexto");
            }
            // Adormece por um curto período antes de verificar novamente
            try {
                Thread.sleep(5000); // intervalo
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void notificarAdministrador(String mensagem) {
        // Isso pode ser feito por slack.
        System.out.println("Notificação para administrador: " + mensagem);
    }

    public Double getEmUso() {
        return processador.getUso();
    }

    public Long getFrequencia() {
        return processador.getFrequencia();
    }

    public void setIdProcessadorTotemValidado(Integer idTotem) {
        idProcessador = getIdComponente(String.valueOf(TipoEnum.PROCESSADOR), idTotem);
    }

    public void setIdProcessador(Integer idProcessador) {
        this.idProcessador = idProcessador;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Fabricante: ").append(processador.getFabricante()).append("\n");
        sb.append("Nome: ").append(processador.getNome()).append("\n");
//        sb.append("ID: ").append(processador.getId()).append("\n");
//        sb.append("Identificador: ").append(processador.getIdentificador()).append("\n");
//        sb.append("Microarquitetura: ").append(processador.getMicroarquitetura()).append("\n");
        sb.append("Frequência: ").append(processador.getFrequencia()).append("\n");
//        sb.append("Número de Pacotes Físicos: ").append(processador.getNumeroPacotesFisicos()).append("\n");
//        sb.append("Número de CPUs Fisícas: ").append(processador.getNumeroCpusFisicas()).append("\n");
//        sb.append("Número de CPUs Lógicas: ").append(processador.getNumeroCpusLogicas()).append("\n");
        sb.append("Em Uso: ").append(String.format("%d.2", processador.getUso().shortValue())).append("%").append("\n");
        return sb.toString();
    }
}
