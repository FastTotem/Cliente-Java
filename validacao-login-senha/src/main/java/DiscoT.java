import com.github.britooo.looca.api.group.discos.Disco;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import oshi.hardware.HWDiskStore;

import java.io.File;
import java.text.DecimalFormat;

public class DiscoT {
    private Integer idDisco;
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
    private Long leituras;
    private JdbcTemplate jdbcTemplate; // Adicione o JdbcTemplate como um membro da classe

    public DiscoT(Disco discoGrupo) {
        this.disco = discoGrupo;
        this.tamanho = disco.getTamanho();
        this.escritas = disco.getEscritas();
        this.bytesDeLeituras = disco.getBytesDeLeitura();
        this.bytesDeEscritas = disco.getBytesDeEscritas();
        this.nome = disco.getNome();
        this.modelo = disco.getModelo();
        this.tempoInsert = 60;
        this.lastRead = bytesDeLeituras;
        this.lastWrite = bytesDeEscritas;
    }

    public Long calcularReadWrite() {
        try {
            bytesDeLeituras = disco.getBytesDeLeitura();
            bytesDeEscritas = disco.getBytesDeEscritas();
            Integer idComponente = getIdDisco(); // Substitua idTotem pelo ID correspondente
            if (idComponente == null) {
                System.out.println("Componente DISCO não encontrado para o totem ");
                return null; // Ou qualquer outra ação apropriada ao seu caso
            }
//         System.out.println(String.format("((bytesDeEscritas[%d] - lastWrite[%d]) + (bytesDeLeituras[%d] - lastRead[%d])) / tempoInsert[%d];",bytesDeEscritas,lastWrite,bytesDeLeituras,lastRead,tempoInsert));
            Long resposta = ((bytesDeEscritas - lastWrite) + (bytesDeLeituras - lastRead)) / tempoInsert;
            lastWrite = bytesDeEscritas;
            lastRead = bytesDeLeituras;
            return resposta;
        } catch (EmptyResultDataAccessException e) {
            System.out.println("Erro ao buscar o componente DISCO.");
            e.printStackTrace(); // Trate a exceção conforme necessário ou lance-a para ser tratada em um nível superior
            return null; // Ou outra ação apropriada ao seu caso
        }
    }

    public Double showTotal() {
        File disk = new File("/"); // diretório raiz do disco
        long totalSpace = disk.getTotalSpace(); // tamanho total do disco em bytes

        // converter bytes para GB
        Double totalGB = (double) totalSpace / (1024 * 1024 * 1024);

        Double value = totalGB;
        DecimalFormat df = new DecimalFormat("#,##0.00"); // define o padrão de formatação
        String formattedValue = df.format(value); // formata o valor double
        Double numero = Double.parseDouble(formattedValue.replace(",", "."));

        return numero;
    }

    public Double showDisponivel() {
        File disk = new File("/"); // diretório raiz do disco
        long freeSpace = disk.getFreeSpace(); // espaço livre em bytes

        // converter bytes para GB
        Double freeGB = (double) freeSpace / (1024 * 1024 * 1024);

        Double value = freeGB;
        DecimalFormat df = new DecimalFormat("#,##0.00"); // define o padrão de formatação
        String formattedValue = df.format(value); // formata o valor double
        Double numero = Double.parseDouble(formattedValue.replace(",", "."));

        return numero;

    }

    public Double showUsado() {
        File disk = new File("/"); // diretório raiz do disco
        long totalSpace = disk.getTotalSpace(); // tamanho total do disco em bytes
        long usedSpace = totalSpace - disk.getFreeSpace(); // espaço usado em bytes

        Double usedGB = (double) usedSpace / (1024 * 1024 * 1024);

        Double value = usedGB;
        DecimalFormat df = new DecimalFormat("#,##0.00"); // define o padrão de formatação
        String formattedValue = df.format(value); // formata o valor double
        Double numero = Double.parseDouble(formattedValue.replace(",", "."));

        return numero;
    }


    private static Double byteConverterMega(long bytes) {
        return (double) bytes / (1024 * 1024);
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

    public void setTamanho(Long tamanho) {
        this.tamanho = tamanho;
    }

    public Long getBytesDeEscritas() {
        return disco.getBytesDeEscritas();
    }

    public void setBytesDeEscritas(Long bytesDeEscritas) {
        this.bytesDeEscritas = bytesDeEscritas;
    }

    public Long getBytesDeLeituras() {
        return disco.getBytesDeLeitura();
    }

    public void setBytesDeLeituras(Long bytesDeLeituras) {
        this.bytesDeLeituras = bytesDeLeituras;
    }


    public Long getEscritas() {
        return disco.getEscritas();
    }

    public Long getLeituras() {
        return disco.getLeituras();
    }

    public void setLeituras(Long leituras) {
        this.leituras = leituras;
    }

    public Integer getTempoInsert() {
        return tempoInsert;
    }

    public void setTempoInsert(Integer tempoInsert) {
        this.tempoInsert = tempoInsert;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Long getLastRead() {
        return lastRead;
    }

    public void setLastRead(Long lastRead) {
        this.lastRead = lastRead;
    }

    public Long getLastWrite() {
        return lastWrite;
    }

    public void setLastWrite(Long lastWrite) {
        this.lastWrite = lastWrite;
    }

    @Override
    public String toString() {
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


