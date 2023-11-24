import conexao.Conexao;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
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
    private final JdbcTemplate conSqlServer = conexao.getConexaoSqlServer();



    public Componente() {
        this.status = String.valueOf(ParametroAlertaEnum.IDEAL);
    }

    public Boolean componenteJaExistente(){

        try {
            Integer idComponente = con.queryForObject("SELECT idComponente FROM componente WHERE fkTotem = ? AND tipoComponente = ?", Integer.class, fkTotem, tipoComponente);
            return true;
        } catch (EmptyResultDataAccessException e) {
           return false;
        } catch (IncorrectResultSizeDataAccessException exception) {
            return true;
        }

    }

    public Integer inserirComponente() {

        if (!componenteJaExistente()){
            try {

                con.update("INSERT INTO componente (nomeComponente, tipoComponente, fkTotem) VALUES (?,?,?)",
                      nomeComponente, tipoComponente, fkTotem);
//                conSqlServer.update("INSERT INTO componente (nomeComponente, tipoComponente, fkTotem) VALUES (?,?,?)",
//                      nomeComponente, tipoComponente, fkTotem);
                System.out.println("Componente inserido!");

                Integer idComponente = con.queryForObject("SELECT idComponente FROM componente WHERE fkTotem = ? AND tipoComponente = ?", Integer.class, fkTotem, tipoComponente);

                return idComponente;

            } catch (Exception e) {
                Logger.logInfo("Erro ao inserir componente - " + e, Componente.class);

            }

        } else if (Objects.equals(tipoComponente, String.valueOf(TipoEnum.DISCO))) {

            try {

                con.update("INSERT INTO componente (nomeComponente, tipoComponente, fkTotem) VALUES (?,?,?)",
                        nomeComponente, tipoComponente, fkTotem);

                Integer idComponente = con.queryForObject("SELECT idComponente FROM componente WHERE fkTotem = ? AND nomeComponente = ?", Integer.class, fkTotem, nomeComponente);

                return idComponente;
            } catch (Exception e) {
                e.printStackTrace();
                Logger.logInfo("Erro ao inserir componente - Disco" + e, Componente.class);
            }


        }

        return null;

    }

    ;

    protected void inserirCapturaComponente(Long valor, String tipoCaptura) {

        con.update("INSERT INTO captura (valor, tipo, dataHora, fkComponente, fkTotem) VALUES (?,?,?,?,?)",
              valor, tipoCaptura, LocalDateTime.now(), idComponente, fkTotem);
        System.out.println("Captura realizada!");
    }

    protected void verificarStatus(Double valor) {
        ParametroAlerta parametroAlerta = con.queryForObject("SELECT * FROM parametroAlerta WHERE componente = ?",
              new BeanPropertyRowMapper<>(ParametroAlerta.class), tipoComponente);

        if (valor >= parametroAlerta.getNotificacao()) {
            if (valor > parametroAlerta.getIdeal() && valor <= parametroAlerta.getAlerta()) {
                if (!this.status.equals(String.valueOf(ParametroAlertaEnum.ALERTA))) {
                    this.status = String.valueOf(ParametroAlertaEnum.ALERTA);
                    // mandar mensagem alerta
                }
            } else if (valor <= parametroAlerta.getCritico()) {
                if (!this.status.equals(String.valueOf(ParametroAlertaEnum.CRITICO))) {
                    this.status = String.valueOf(ParametroAlertaEnum.CRITICO);
                    // mandar mensagem critico
                }
            }
        }
    }

    protected void inserirCapturaComponente(Double valor, String tipoCaptura) {

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
            Logger.logInfo("Componente não encontrado" + e, Componente.class);
            throw new RuntimeException("Componente não encontrado para o tipo: " + tipoComponente);
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
        try {
            nomeComponente = con.queryForObject("SELECT nomeComponente FROM componente WHERE tipoComponente = ? AND fkTotem = ?",
                  String.class, tipoComponente, fkTotem);
        } catch (EmptyResultDataAccessException e) {
            // Se resultado vazio definir uma mensagem de log ou lançar uma exceção
            Logger.logInfo("Componente não encontrado" + e, UsbT.class); // Valor padrão ou mensagem de erro
            throw new RuntimeException("Componente não encontrado para o tipo: " + tipoComponente);
        }
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
