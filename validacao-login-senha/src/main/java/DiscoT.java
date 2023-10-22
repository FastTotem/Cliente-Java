import com.github.britooo.looca.api.group.discos.Disco;

public class DiscoT {
    private Disco disco;
    private Long tamanho;
    private Long escritas;
    private Long bytesDeEscritas;
    private String nome;
    private String modelo;
    private Long bytesDeLeituras;


    private Integer tempoInsert;
    private Long lastRead;
    private Long lastWrite;

    public DiscoT(Disco disco) {
        this.disco = disco;
        this.tamanho = disco.getTamanho();
        this.escritas = disco.getEscritas();
        this.bytesDeLeituras = disco.getBytesDeLeitura();
        this.bytesDeEscritas = disco.getBytesDeEscritas();
        this.nome = disco.getNome();
        this.modelo = disco.getModelo();
        this.tempoInsert = 60;
        this.lastRead = bytesDeEscritas;
        this.lastWrite = bytesDeEscritas;
    }

    public Long calcularReadWrite(){
        Long resposta = (getBytesDeEscritas() - lastWrite) + (getBytesDeLeituras() - lastRead) / tempoInsert;
        return resposta;
    }


    public Long getTamanho() {
        return tamanho;
    }

    public Long getEscritas() {
        return escritas;
    }

    public Long getBytesDeEscritas() {
        return disco.getBytesDeEscritas();
    }

    public String getNome() {
        return nome;
    }

    public String getModelo() {
        return modelo;
    }

    public Long getBytesDeLeituras() { return disco.getBytesDeLeitura(); }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
//        sb.append("Nome: ").append(disco.getNome()).append("\n");
//        sb.append("Modelo: ").append(disco.getModelo()).append("\n");
//        sb.append("Serial: ").append(disco.getSerial()).append("\n");
        sb.append("Tamanho: ").append(tamanho).append("\n");
//        sb.append("Leituras: ").append(disco.getLeituras()).append("\n");
//        sb.append("Bytes de leitura: ").append(disco.getBytesDeLeitura()).append("\n");
        sb.append("Escritas: ").append(escritas).append("\n");
        sb.append("Bytes de escritas: ").append(bytesDeEscritas).append("\n");
//        sb.append("Tamanho atual da fila: ").append(disco.getTamanhoAtualDaFila()).append("\n");
//        sb.append("Tempo de transferência: ").append(disco.getTempoDeTransferencia()).append("\n");
        return sb.toString();
    }
}
