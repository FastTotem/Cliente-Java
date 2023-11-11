import com.github.britooo.looca.api.group.processador.Processador;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;

public class ProcessadorT extends Componente {
    private Processador processador;
    private Integer idProcessador;
    private Double emUso;
    private Long frequencia;

    public ProcessadorT() {
        this.hal = new oshi.SystemInfo().getHardware();
        this.processador = new Processador();
    }

    private HardwareAbstractionLayer hal;
    public void inserirCapturaUsoProcessador(){
        emUso = processador.getUso();
//        frequencia = processador.getFrequencia();
        inserirCapturaComponente(emUso, String.valueOf(TipoEnum.PROCESSADOR), idProcessador);
//        inserirCapturaComponente(frequencia, String.valueOf(TipoCapturaEnum.PROCESSADOR));

    }
    public void monitorarUsoProcessador() {
        CentralProcessor processor = hal.getProcessor();
       // Logger.log(toString(), ProcessadorT.class);

        while (true) {
        double[] systemLoadAverage = processor.getSystemLoadAverage(2); // 3 indica a média dos últimos 3 segundos - aceita de 1 a 3
            // Se a carga do sistema atingir 100%, registra no log
            if (processador.getUso() >= 0.1) {
              Logger.log("Carga do sistema atingiu " + processador.getUso().shortValue()+ "%");
            }

        // Adormece por um curto período antes de verificar novamente
        try {
            Thread.sleep(1000); // Ajuste o intervalo conforme necessário
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
    public String toString(){
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
        sb.append("Em Uso: ").append(String.format("%.1f", processador.getUso())).append("\n");
        return sb.toString();
    }


}
