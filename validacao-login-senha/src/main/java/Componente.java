import conexao.Conexao;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public abstract class Componente {

    protected Integer idComponente;
    protected String nomeComponente;
    protected String tipoComponente;
    protected Integer fkTotem;
    private String status;
    private final Conexao conexao = new Conexao();
    private final JdbcTemplate con = conexao.getConexaoDoBanco();
    // private final JdbcTemplate conSqlServer = conexao.getConexaoSqlServer();

    public Componente() {
        this.status = String.valueOf(ParametroAlertaEnum.IDEAL);
    }

    public List<Componente> verificarComponente(){
        List<Componente> componentes = con.query("SELECT * FROM componente where fkTotem = ? and tipoComponente = ?",
                new BeanPropertyRowMapper<>(Componente.class), fkTotem, tipoComponente);

        return componentes;
    }

    public Integer inserirComponente(){

        List<Componente> componentes = verificarComponente();
        if (componentes.isEmpty()){
            try {

                con.update("INSERT INTO componente (nomeComponente, tipoComponente, fkTotem) VALUES (?,?,?)",
                        nomeComponente, tipoComponente, fkTotem);
                // conSqlServer.update("INSERT INTO componente (nomeComponente, tipoComponente, fkTotem) VALUES (?,?,?)",
                //        nomeComponente, tipoComponente, fkTotem);
                System.out.println("Componente inserido!");

                Integer idComponente = con.queryForObject("SELECT idComponente FROM componente WHERE fkTotem = ? AND tipoComponente = ?", Integer.class, fkTotem, tipoComponente);

                return idComponente;

            } catch (Exception e){
                System.out.println(LocalDateTime.now() + " Erro ao inserir componente - " + e);
            }

        } else if (Objects.equals(tipoComponente, String.valueOf(TipoEnum.DISCO))){

            con.update("INSERT INTO componente (nomeComponente, tipoComponente, fkTotem) VALUES (?,?,?)",
                    nomeComponente, tipoComponente, fkTotem);

            Integer idComponente = con.queryForObject("SELECT idComponente FROM componente WHERE fkTotem = ? AND nomeComponente = ?", Integer.class, fkTotem, nomeComponente);

            return idComponente;

        }

        return null;

    };

    protected void inserirCapturaComponente(Long valor, String tipoCaptura){

        con.update("INSERT INTO captura (valor, tipo, dataHora, fkComponente, fkTotem) VALUES (?,?,?,?,?)",
                valor, tipoCaptura, LocalDateTime.now(), idComponente, fkTotem);

        System.out.println("Captura realizada!");

    }

    protected void verificarStatus(Double valor){
        ParametroAlerta parametroAlerta = con.queryForObject("SELECT * FROM parametroAlerta WHERE componente = ?",
                new BeanPropertyRowMapper<>(ParametroAlerta.class), tipoComponente);

        if (valor >= parametroAlerta.getNotificacao()){
            if (valor > parametroAlerta.getIdeal() && valor <= parametroAlerta.getAlerta()){
                if (!this.status.equals(String.valueOf(ParametroAlertaEnum.ALERTA))){
                    this.status = String.valueOf(ParametroAlertaEnum.ALERTA);
                    // mandar mensagem alerta
                }
            } else if (valor <= parametroAlerta.getCritico()) {
                if (!this.status.equals(String.valueOf(ParametroAlertaEnum.CRITICO))){
                    this.status = String.valueOf(ParametroAlertaEnum.CRITICO);
                    // mandar mensagem critico
                }
            }
        }

    }

    protected void inserirCapturaComponente(Double valor, String tipoCaptura){

        con.update("INSERT INTO captura (valor, tipo, dataHora, fkComponente, fkTotem) VALUES (?,?,?,?,?)",
                valor, tipoCaptura, LocalDateTime.now(), idComponente, fkTotem);

        System.out.println("Captura realizada!");

    }

    protected void notificarAdministrador(String mensagem) {
        // Isso pode ser feito por slack.
        System.out.println("Notificação para administrador: " + mensagem);
    }

    protected List<Integer> getListaIdComponente(String tipoComponente) {
        List<Integer> idComponentes = con.queryForList("SELECT idComponente FROM componente WHERE tipoComponente = ? AND fkTotem = ?",
                Integer.class, tipoComponente, fkTotem);
        return idComponentes;
    }

    protected Integer getIdComponente(String tipoComponente, Integer idTotem) {
        Integer idComponente = null;
        try {
            idComponente = con.queryForObject(
                  "SELECT idComponente FROM componente WHERE tipoComponente = ? AND fkTotem = ?",
                  Integer.class, tipoComponente, idTotem
            );
        } catch (EmptyResultDataAccessException e) {
            // Tratamento para caso não encontre nenhum resultado
            // Por exemplo, você pode definir um valor padrão para id ou lançar uma exceção personalizada
            System.out.println("Nenhum resultado encontrado para a consulta.");
            // Ou então, defina um valor padrão para o ID ou lance uma exceção personalizada
            // Exemplo: idComponente = 0; ou throw new MinhaExcecao("Nenhum resultado encontrado");
        }
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

    public String getTipoComponente() {
        return tipoComponente;
    }

    public void setTipoComponente(String tipoComponente) {
        this.tipoComponente = tipoComponente;
    }

    public Integer getFkTotem() {
        return fkTotem;
    }

    public void setFkTotem(Integer fkTotem) {
        this.fkTotem = fkTotem;
    }

}
