import conexao.Conexao;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Componente {

    private Integer idComponente;
    private String nomeComponente;
    private String tipoComponente;
    private Integer fkTotem;
    private final Conexao conexao = new Conexao();
    private final JdbcTemplate con = conexao.getConexaoDoBanco();

    public Componente() {}

    public List<Componente> verificarComponente(){
        List<Componente> componentes = con.query("SELECT * FROM componente where fkTotem = ? and tipoComponente = ?",
                new BeanPropertyRowMapper<>(Componente.class), fkTotem, tipoComponente);

        return componentes;
    }

    public Integer inserirComponente(String tipoComponente, String nomeComponente){

        this.tipoComponente = tipoComponente;
        List<Componente> componentes = verificarComponente();
        if (componentes.isEmpty()){
            con.update("INSERT INTO componente (nomeComponente, tipoComponente, fkTotem) VALUES (?,?,?)",
                    nomeComponente, tipoComponente, fkTotem);
            System.out.println("Componente inserido!");

            Integer idComponente = con.queryForObject("SELECT idComponente FROM componente WHERE fkTotem = ? AND tipoComponente = ?", Integer.class, fkTotem, tipoComponente);

            return idComponente;

        } else if (Objects.equals(tipoComponente, String.valueOf(TipoEnum.DISCO))){

            con.update("INSERT INTO componente (nomeComponente, tipoComponente, fkTotem) VALUES (?,?,?)",
                    nomeComponente, tipoComponente, fkTotem);

            Integer idComponente = con.queryForObject("SELECT idComponente FROM componente WHERE fkTotem = ? AND nomeComponente = ?", Integer.class, fkTotem, nomeComponente);

            return idComponente;

        }

        return null;

    };

    void notificarAdministrador(String mensagem) {
        // Isso pode ser feito por slack.
        System.out.println("Notificação para administrador: " + mensagem);
    }

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

    protected List<Integer> getListaIdComponente(String tipoComponente) {
        List<Integer> idComponentes = con.queryForList("SELECT idComponente FROM componente WHERE tipoComponente = ? AND fkTotem = ?",
                Integer.class, tipoComponente, fkTotem);
        return idComponentes;
    }

    protected Integer getIdComponente(String tipoComponente, Integer idTotem) {
        idComponente = con.queryForObject("SELECT idComponente FROM componente WHERE tipoComponente = ? AND fkTotem = ?",
                Integer.class, tipoComponente, idTotem);
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

    public String getNomeComponente(String tipoComponente) {
        nomeComponente = con.queryForObject("SELECT nomeComponente FROM componente WHERE tipoComponente = ? AND fkTotem = ?",
                String.class, tipoComponente, fkTotem);
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
