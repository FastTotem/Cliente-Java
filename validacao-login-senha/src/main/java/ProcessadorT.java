import com.github.britooo.looca.api.group.processador.Processador;

public class ProcessadorT extends Componente {
    private Processador processador;
    private Integer idProcessador;
    private Double emUso;
    private Long frequencia;

    public ProcessadorT() {
        this.processador = new Processador();
    }

    public void inserirCapturaUsoProcessador(){

        emUso = processador.getUso();
//        frequencia = processador.getFrequencia();
        inserirCapturaComponente(emUso, String.valueOf(TipoEnum.PROCESSADOR), idProcessador);
//        inserirCapturaComponente(frequencia, String.valueOf(TipoCapturaEnum.PROCESSADOR));

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
