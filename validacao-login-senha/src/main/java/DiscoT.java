import com.github.britooo.looca.api.group.discos.Disco;

public class DiscoT {
    private Integer idDisco;
    private Disco disco;
    private Long tamanho;
    private Long escritas;
    private Long leituras;
    private Long bytesDeEscritas;
    private String nome;
    private String modelo;

    public DiscoT(Disco disco) {
        this.disco = disco;
        this.tamanho = disco.getTamanho();
        this.escritas = disco.getEscritas();
        this.leituras = disco.getLeituras();
        this.bytesDeEscritas = disco.getBytesDeEscritas();
        this.nome = disco.getNome();
        this.modelo = disco.getModelo();
    }

    public Integer getIdDisco() {
        return idDisco;
    }

    public void setIdDisco(Integer idDisco) {
        this.idDisco = idDisco;
    }

    public Long getTamanho() {
        return tamanho;
    }

    public Long getEscritas() {
        return disco.getEscritas();
    }

    public Long getBytesDeEscritas() {
        return bytesDeEscritas;
    }

    public String getNome() {
        return nome;
    }

    public String getModelo() {
        return modelo;
    }

    public Long getLeituras() {
        return disco.getLeituras();
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
//        sb.append("Nome: ").append(disco.getNome()).append("\n");
//        sb.append("Modelo: ").append(disco.getModelo()).append("\n");
//        sb.append("Serial: ").append(disco.getSerial()).append("\n");
        sb.append("Tamanho: ").append(tamanho).append("\n");
        sb.append("Leituras: ").append(disco.getLeituras()).append("\n");
//        sb.append("Bytes de leitura: ").append(disco.getBytesDeLeitura()).append("\n");
        sb.append("Escritas: ").append(escritas).append("\n");
        sb.append("Bytes de escritas: ").append(bytesDeEscritas).append("\n");
//        sb.append("Tamanho atual da fila: ").append(disco.getTamanhoAtualDaFila()).append("\n");
//        sb.append("Tempo de transferência: ").append(disco.getTempoDeTransferencia()).append("\n");
        return sb.toString();
    }
}
