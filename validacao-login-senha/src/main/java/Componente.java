import conexao.Conexao;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Componente {

    private Integer idComponente;
    private String nomeComponente;
    private Integer fkTotem;
    private final Conexao conexao = new Conexao();
    private final JdbcTemplate con = conexao.getConexaoDoBanco();

    public Componente() {}

    public List<Componente> verificarComponente(){
        List<Componente> componentes = con.query("SELECT * FROM componente where fkTotem = ? and nomeComponente = ?",
                new BeanPropertyRowMapper<>(Componente.class), fkTotem, nomeComponente);

        return componentes;
    }

    public Integer inserirComponentes(String nomeComponente){

        this.nomeComponente = nomeComponente;
        List<Componente> componentes = verificarComponente();
        if (componentes.isEmpty()){
            con.update("INSERT INTO componente (nomeComponente, fkTotem) VALUES (?,?)",
                    nomeComponente, fkTotem);
            System.out.println("Componente inserido!");

            Integer idComponente = con.queryForObject("SELECT idComponente FROM componente WHERE fkTotem = ? AND nomeComponente = ?", Integer.class, fkTotem, nomeComponente);

            return idComponente;

        } else if (Objects.equals(nomeComponente, String.valueOf(TipoEnum.DISCO))){

            con.update("INSERT INTO componente (nomeComponente, fkTotem) VALUES (?,?)",
                    nomeComponente, fkTotem);

            Integer idComponente = con.queryForObject("SELECT idComponente FROM componente WHERE fkTotem = ? AND nomeComponente = ?", Integer.class, fkTotem, nomeComponente);

            return idComponente;

        }

        return null;

    };

    protected void inserirCapturaComponente(Long valor, String tipoCaptura, Integer idComponente){

        con.update("INSERT INTO captura (valor, tipo, dataHora, fkComponente, fkTotem) VALUES (?,?,?,?,?)",
                valor, tipoCaptura, LocalDateTime.now(), idComponente, fkTotem);

        System.out.println("Captura realizada!");

    }

    protected void inserirCapturaComponente(Double valor, String tipoCaptura, Integer idComponente){

        con.update("INSERT INTO captura (valor, tipo, dataHora, fkComponente, fkTotem) VALUES (?,?,?,?,?)",
                valor, tipoCaptura, LocalDateTime.now(), idComponente, fkTotem);

        System.out.println("Captura realizada!");

    }

    protected List<Integer> getListaIdComponente(String nomeComponente) {
        List<Integer> idComponentes = con.queryForList("SELECT idComponente FROM componente WHERE nomeComponente = ? AND fkTotem = ?",
                Integer.class, nomeComponente, fkTotem);
        return idComponentes;
    }

    protected Integer getIdComponente(String nomeComponente, Integer idTotem) {
        idComponente = con.queryForObject("SELECT idComponente FROM componente WHERE nomeComponente = ? AND fkTotem = ?",
                Integer.class, nomeComponente, idTotem);
        return idComponente;
    }

    public Integer getIdComponente() {
        return idComponente;
    }

    public void setIdComponente(Integer idComponente) {
        this.idComponente = idComponente;
    }

    public String getNomeComponente() {
        return nomeComponente;
    }

    public void setNomeComponente(String nomeComponente) {
        this.nomeComponente = nomeComponente;
    }

    public Integer getFkTotem() {
        return fkTotem;
    }

    public void setFkTotem(Integer fkTotem) {
        this.fkTotem = fkTotem;
    }

}
