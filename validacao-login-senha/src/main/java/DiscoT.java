import com.github.britooo.looca.api.group.discos.Disco;
import com.github.britooo.looca.api.group.discos.DiscoGrupo;

public class DiscoT {
    private DiscoGrupo grupoDeDiscos;
    private Disco disco;

    private Long tamanho;
    private Long escritas;
    private Long bytesDeEscritas;

    public DiscoT() {
        this.grupoDeDiscos = new DiscoGrupo();
        this.disco = grupoDeDiscos.getDiscos().get(0);
    }

    public Long getTamanho() {
        return disco.getTamanho();
    }

    public Long getEscritas() {
        return disco.getEscritas();
    }

    public Long getBytesDeEscritas() {
        return disco.getBytesDeEscritas();
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Nome: ").append(disco.getNome()).append("\n");
        sb.append("Modelo: ").append(disco.getModelo()).append("\n");
//        sb.append("Serial: ").append(disco.getSerial()).append("\n");
        sb.append("Tamanho: ").append(disco.getTamanho()).append("\n");
//        sb.append("Leituras: ").append(disco.getLeituras()).append("\n");
//        sb.append("Bytes de leitura: ").append(disco.getBytesDeLeitura()).append("\n");
        sb.append("Escritas: ").append(disco.getEscritas()).append("\n");
        sb.append("Bytes de escritas: ").append(disco.getBytesDeEscritas()).append("\n");
//        sb.append("Tamanho atual da fila: ").append(disco.getTamanhoAtualDaFila()).append("\n");
//        sb.append("Tempo de transferência: ").append(disco.getTempoDeTransferencia()).append("\n");
        return sb.toString();
    }

}
