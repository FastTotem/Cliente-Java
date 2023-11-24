import com.github.britooo.looca.api.group.processador.Processador;

public class ProcessadorT extends Componente {
    private Processador processador;
    private Double emUso;
    private Long frequenciaEmHertz;
    private Double frequenciaEmGHz;
    private String modelo;
    private Integer NumeroCors;

    public ProcessadorT() {
        this.processador = new Processador();
        this.tipoComponente = String.valueOf(TipoEnum.PROCESSADOR);
    }

    public void inserirCapturaUsoProcessador() {
        emUso = processador.getUso();
//        frequencia = processador.getFrequencia();
        inserirCapturaComponente(emUso, String.valueOf(TipoEnum.PROCESSADOR));
//        inserirCapturaComponente(frequencia, String.valueOf(TipoCapturaEnum.PROCESSADOR));

    }

    public void monitorarUsoProcessador() {
        while (true) {
            // Se a carga do sistema atingir 80%, registra no log
            if (processador.getUso() >= 80.0) {
                Logger.logInfo("[ALERTA] Carga do sistema atingiu " + processador.getUso().shortValue() + "%", ProcessadorT.class);
            } else if (processador.getUso() >= 99.0) {
                Logger.logInfo("[SEVERO] Carga do sistema atingiu " + processador.getUso().shortValue() + "%", ProcessadorT.class);
            } else {
                Logger.logInfo("CPU Info: \n" + this, ProcessadorT.class);
            }
            // Adormece por um curto período antes de verificar novamente
            try {
                Thread.sleep(10000);// Aguarda 10 segundos antes de verificar novamente
            } catch (InterruptedException e) {
                Logger.logInfo("Erro para monitorar uso do Processador.\" " + e, Componente.class);
                e.printStackTrace();
            }
        }
    }

    public String getModelo() {
        return modelo;
    }

    public Integer getNumeroCors() {
        return NumeroCors;
    }

    public Double getEmUso() {
        Double usoProcessador = processador.getUso();
        Double usoProcessadorPorcentagem = (usoProcessador / 100) * 100;
        return usoProcessadorPorcentagem;
    }

    public Double getFrequencia() {
        frequenciaEmHertz = processador.getFrequencia();
        frequenciaEmGHz = (frequenciaEmHertz / 1000000000.0);
        return frequenciaEmGHz;
    }

    public void setIdProcessadorTotemValidado(Integer idTotem) {
        idComponente = getIdComponente(String.valueOf(TipoEnum.PROCESSADOR), idTotem);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Fabricante: ").append(processador.getFabricante()).append("\n");
        sb.append("Modelo: ").append(processador.getNome()).append("\n");
//        sb.append("ID: ").append(processador.getId()).append("\n");
//        sb.append("Identificador: ").append(processador.getIdentificador()).append("\n");
//        sb.append("Microarquitetura: ").append(processador.getMicroarquitetura()).append("\n");
        sb.append("Frequência: ").append(getFrequencia() + " GHz").append("\n");
//        sb.append("Número de Pacotes Físicos: ").append(processador.getNumeroPacotesFisicos()).append("\n");
//        sb.append("Número de CPUs Fisícas: ").append(processador.getNumeroCpusFisicas()).append("\n");
//        sb.append("Número de CPUs Lógicas: ").append(processador.getNumeroCpusLogicas()).append("\n");
        sb.append("Em Uso: ").append(Math.round(getEmUso()) + "%").append("\n");
        return sb.toString();
    }
}
