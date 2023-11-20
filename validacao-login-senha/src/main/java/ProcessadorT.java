import com.github.britooo.looca.api.group.processador.Processador;

public class ProcessadorT extends Componente {
    private Processador processador;
    private Double emUso;
    private Long frequencia;

    public ProcessadorT() {
        this.processador = new Processador();
        this.tipoComponente = String.valueOf(TipoEnum.PROCESSADOR);
    }

    public void inserirCapturaUsoProcessador(){

        emUso = processador.getUso();
//        frequencia = processador.getFrequencia();
        inserirCapturaComponente(emUso, String.valueOf(TipoEnum.PROCESSADOR));
//        inserirCapturaComponente(frequencia, String.valueOf(TipoCapturaEnum.PROCESSADOR));

    }

    public Double getEmUso() {
        return processador.getUso();
    }

    public Long getFrequencia() {
        return processador.getFrequencia();
    }

    public void setIdProcessadorTotemValidado(Integer idTotem) {
        idComponente = getIdComponente(String.valueOf(TipoEnum.PROCESSADOR), idTotem);
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
