import com.github.britooo.looca.api.group.discos.Disco;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.File;
import java.text.DecimalFormat;

public class DiscoT {
    private Integer idDisco;
    private Disco disco;
    private Long tamanho;
    private Long bytesDeEscritas;
    private String nome;
    private String modelo;
    private Long bytesDeLeituras;
    private Integer tempoInsert;
    private Long ultimaEscrita;
    private Long ultimaLeitura;

    public DiscoT(Disco discoGrupo) {
        this.disco = discoGrupo;
        this.tamanho = disco.getTamanho();
        this.ultimaEscrita = disco.getEscritas();
        this.bytesDeLeituras = disco.getBytesDeLeitura();
        this.bytesDeEscritas = disco.getBytesDeEscritas();
        this.nome = disco.getNome();
        this.modelo = disco.getModelo();
        this.tempoInsert = 60;
        this.ultimaLeitura = bytesDeLeituras;
        this.ultimaEscrita = bytesDeEscritas;
    }

    public Double calcularLeituraEscrita() {
        bytesDeLeituras = disco.getBytesDeLeitura();
        bytesDeEscritas = disco.getBytesDeEscritas();
        Integer idComponente = getIdDisco();
        if (idComponente == null) {
            Logger.logInfo("Id de disco não encontrado para realizar cálculo - ", DiscoT.class);
            return null;
        }
        Double resposta = (double) (((bytesDeEscritas - ultimaEscrita) + (bytesDeLeituras - ultimaLeitura)) / tempoInsert);
        ultimaEscrita = bytesDeEscritas;
        ultimaLeitura = bytesDeLeituras;
        return resposta;
    }

    public Double calcularPorcentagemArmazenada(){
        return (bytesDeEscritas.doubleValue()/tamanho.doubleValue())*100;
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

    public Integer getIdDisco() {
        return idDisco;
    }

    public void setIdDisco(Integer idDisco) {
        this.idDisco = idDisco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getBytesDeEscritas() {
        return bytesDeEscritas;
    }

    public void setBytesDeEscritas(Long bytesDeEscritas) {
        this.bytesDeEscritas = bytesDeEscritas;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Long getBytesDeLeituras() {
        return bytesDeLeituras;
    }

    public void setBytesDeLeituras(Long bytesDeLeituras) {
        this.bytesDeLeituras = bytesDeLeituras;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nTamanho Total: ").append(showTotal()).append("\n");
        sb.append("Espaço Usado: ").append(showUsado()).append("\n");
        sb.append("Espaço Disponível: ").append(showDisponivel()).append("\n");
        sb.append("Tempo de transferência: ").append(disco.getTempoDeTransferencia().shortValue()).append("\n");
        return sb.toString();
    }
}


