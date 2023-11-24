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

    public Componente() {
        this.status = String.valueOf(ParametroAlertaEnum.IDEAL);
    }

    public Boolean componenteJaExistente() {
        try {
            Integer idComponente = con.queryForObject(
                  "SELECT idComponente FROM componente WHERE fkTotem = ? AND tipoComponente = ?",
                  Integer.class, fkTotem, tipoComponente
            );
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

//    public Integer inserirComponente() {
//        if (!componenteJaExistente()){
//            try {
//                con.update("INSERT INTO componente (nomeComponente, tipoComponente, fkTotem) VALUES (?,?,?)",
//                      nomeComponente, tipoComponente, fkTotem);
////                conSqlServer.update("INSERT INTO componente (nomeComponente, tipoComponente, fkTotem) VALUES (?,?,?)",
////                      nomeComponente, tipoComponente, fkTotem);
//                System.out.println("Componente inserido!");
//
//                Integer idComponente = con.queryForObject("SELECT idComponente FROM componente WHERE fkTotem = ? AND tipoComponente = ?", Integer.class, fkTotem, tipoComponente);
//
//                return idComponente;
//
//            } catch (Exception e) {
//                Logger.logInfo("Erro ao inserir componente - " + e, Componente.class);
//
//            }
//
//        } else if (Objects.equals(tipoComponente, String.valueOf(TipoEnum.DISCO))) {
//
//            try {
//
//                con.update("INSERT INTO componente (nomeComponente, tipoComponente, fkTotem) VALUES (?,?,?)",
//                        nomeComponente, tipoComponente, fkTotem);
//
//                Integer idComponente = con.queryForObject("SELECT idComponente FROM componente WHERE fkTotem = ? AND nomeComponente = ?", Integer.class, fkTotem, nomeComponente);
//
//                return idComponente;
//            } catch (Exception e) {
//                e.printStackTrace();
//                Logger.logInfo("Erro ao inserir componente - Disco" + e, Componente.class);
//            }
//
//
//        }
//
//        return null;
//
//    }

    public Integer inserirComponente() {
        if (!componenteJaExistente()) {
            try {
                con.update("INSERT INTO componente (nomeComponente, tipoComponente, fkTotem) VALUES (?,?,?)",
                      nomeComponente, tipoComponente, fkTotem);

                System.out.println("Componente inserido!");

                return con.queryForObject(
                      "SELECT idComponente FROM componente WHERE fkTotem = ? AND tipoComponente = ?",
                      Integer.class, fkTotem, tipoComponente
                );

            } catch (Exception e) {
                Logger.logInfo("Erro ao inserir componente - " + e, Componente.class);
            }
        }
        return null;
    }

    protected void verificarStatus(Double valor) {
        ParametroAlerta parametroAlerta = con.queryForObject(
              "SELECT * FROM parametroAlerta WHERE componente = ?",
              new BeanPropertyRowMapper<>(ParametroAlerta.class), tipoComponente
        );

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

    protected List<Integer> getListaIdComponente(String tipoComponente) {
        return con.queryForList(
              "SELECT idComponente FROM componente WHERE tipoComponente = ? AND fkTotem = ?",
              Integer.class, tipoComponente, fkTotem
        );
    }

    protected Integer getIdComponente(String tipoComponente, Integer idTotem) {
        try {
            return con.queryForObject(
                  "SELECT idComponente FROM componente WHERE tipoComponente = ? AND fkTotem = ?",
                  Integer.class, tipoComponente, idTotem
            );
        } catch (EmptyResultDataAccessException e) {
            Logger.logInfo("Componente não encontrado" + e, Componente.class);
            throw new RuntimeException("Componente não encontrado para o tipo: " + tipoComponente);
        }
    }
    public void setIdComponente(Integer idComponente) {
        this.idComponente = idComponente;
    }

    public String getNomeComponente() {
        return nomeComponente;
    }

    public String getNomeComponente(String tipoComponente) {
        try {
            return con.queryForObject(
                  "SELECT nomeComponente FROM componente WHERE tipoComponente = ? AND fkTotem = ?",
                  String.class, tipoComponente, fkTotem
            );
        } catch (EmptyResultDataAccessException e) {
            Logger.logInfo("Componente não encontrado", this.getClass());
            return "Nome do componente não encontrado";
        }
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
