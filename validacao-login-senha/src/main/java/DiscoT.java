public class DiscoT {
    private Long tamanho;
    private Long escritas;
    private Long bytesDeEscritas;
    private String nome;
    private String modelo;

    public DiscoT(Long tamanho, Long escritas, Long bytesDeEscritas, String nome, String modelo) {
        this.tamanho = tamanho;
        this.escritas = escritas;
        this.bytesDeEscritas = bytesDeEscritas;
        this.nome = nome;
        this.modelo = modelo;
    }

    public Long getTamanho() {
        return tamanho;
    }

    public Long getEscritas() {
        return escritas;
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
