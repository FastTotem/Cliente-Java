import conexao.Conexao;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class Totem {

    private Integer idTotem;
    private String nome;
    private String chaveDeAcesso;
    private Integer fkEmpresa;
    private String boardSerialNumber;
    private final Conexao conexao = new Conexao();
    private final JdbcTemplate con = conexao.getConexaoDoBanco();

    public Totem() {
    }

    public Totem(Integer idTotem, String nome, String chaveDeAcesso, Integer fkEmpresa, String boardSerialNumber) {
        this.idTotem = idTotem;
        this.nome = nome;
        this.chaveDeAcesso = chaveDeAcesso;
        this.fkEmpresa = fkEmpresa;
        this.boardSerialNumber = boardSerialNumber;
    }

    public Totem getTotem() {

        try {
            Totem totem = con.queryForObject("SELECT * FROM totem WHERE chaveDeAcesso = ?",
                    new BeanPropertyRowMapper<>(Totem.class), chaveDeAcesso);

            return totem;

        } catch (EmptyResultDataAccessException e) {
            return null;
        }

    }

    public Boolean validarTotemJaAtivo() {
        if (boardSerialNumber != null) {
            return true;
        }
        return false;
    }

    public Integer getIdTotem() {
        return idTotem;
    }

    public void setIdTotem(Integer idTotem) {
        this.idTotem = idTotem;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getChaveDeAcesso() {
        return chaveDeAcesso;
    }

    public void setChaveDeAcesso(String chaveDeAcesso) {
        this.chaveDeAcesso = chaveDeAcesso;
    }

    public Integer getFkEmpresa() {
        return fkEmpresa;
    }

    public void setFkEmpresa(Integer fkEmpresa) {
        this.fkEmpresa = fkEmpresa;
    }

    public String getBoardSerialNumber() {
        return boardSerialNumber;
    }

    public void setBoardSerialNumber(String boardSerialNumber) {
        this.boardSerialNumber = boardSerialNumber;
    }
}
