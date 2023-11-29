import conexao.Conexao;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import slack.Notification;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;

public abstract class Componente {

    protected Integer idComponente;
    protected String nomeComponente;
    protected String tipoComponente;
    protected Integer fkTotem;
    protected Integer fkEmpresa;
    private String status;
    private final Conexao conexao = new Conexao();
    private final JdbcTemplate con = conexao.getConexaoDoBanco();
    private final JdbcTemplate conSqlServer = conexao.getConexaoSqlServer();
    private final Integer OCORRENCIA_ALERTA = 3;
    private Integer qtdeAlerta;
    private Integer qtdeCritico;
    private Double ultimaCaptura;
    private Boolean notificado;
    private static ZoneId zoneSaoPaulo = ZoneId.of("America/Sao_Paulo");


    public Componente() {
        this.status = String.valueOf(ParametroAlertaEnum.IDEAL);
        this.notificado = false;
        qtdeAlerta = 0;
        qtdeCritico = 0;
    }

    public Boolean componenteJaExistente(){

        try {
            Integer idComponente = conSqlServer.queryForObject("SELECT idComponente FROM componente WHERE fkTotem = ? AND tipoComponente = ?", Integer.class, fkTotem, tipoComponente);
            return true;
        } catch (EmptyResultDataAccessException e) {
           return false;
        } catch (IncorrectResultSizeDataAccessException exception) {
            return true;
        }

    }

    public Boolean discoValidado () {
        try {
            conSqlServer.queryForObject("SELECT idComponente FROM componente WHERE fkTotem = ? AND nomeComponente = ?", Integer.class, fkTotem, nomeComponente);
            return false;
        } catch (EmptyResultDataAccessException e) {
            return true;
        }
    }

    public Integer inserirComponente() {

        if (!componenteJaExistente()){
            try {

                conSqlServer.update("INSERT INTO componente (nomeComponente, tipoComponente, fkTotem) VALUES (?,?,?)",
                        nomeComponente, tipoComponente, fkTotem);
                Integer idComponenteInserido = conSqlServer.queryForObject("SELECT idComponente FROM componente WHERE fkTotem = ? AND tipoComponente = ?", Integer.class, fkTotem, tipoComponente);

                con.update("INSERT INTO componente (idComponente, nomeComponente, tipoComponente, fkTotem) VALUES (?,?,?,1)",
                        idComponenteInserido, nomeComponente, tipoComponente);

                System.out.println("Componente inserido!");

                return idComponenteInserido;

            } catch (Exception e) {
                Logger.logInfo("Erro ao inserir componente - " + e, Componente.class);
                e.printStackTrace();
            }

        } else if (Objects.equals(tipoComponente, String.valueOf(TipoEnum.DISCO))) {

            try {

                if (discoValidado()) {
                    conSqlServer.update("INSERT INTO componente (nomeComponente, tipoComponente, fkTotem) VALUES (?,?,?)",
                            nomeComponente, tipoComponente, fkTotem);
                    Integer idComponenteInserido = conSqlServer.queryForObject("SELECT idComponente FROM componente WHERE fkTotem = ? AND nomeComponente = ?", Integer.class, fkTotem, nomeComponente);

                    con.update("INSERT INTO componente (idComponente, nomeComponente, tipoComponente, fkTotem) VALUES (?,?,?,1)",
                            idComponenteInserido, nomeComponente, tipoComponente);


                    return idComponenteInserido;
                }

            } catch (Exception e) {
                e.printStackTrace();
                Logger.logInfo("Erro ao inserir componente - Disco" + e, Componente.class);
            }


        } else {
            if (!Objects.equals(tipoComponente, String.valueOf(TipoEnum.DISCO))) {
                return setIdComponenteTotemValidado();
            }
        }

        return null;

    }

    protected void inserirCapturaComponente(Double valor, String tipoCaptura) {
        try {
            conSqlServer.update("INSERT INTO captura (valor, tipo, dataHora, fkComponente, fkTotem) VALUES (?,?,?,?,?)",
                    valor, tipoCaptura, LocalDateTime.now(zoneSaoPaulo), idComponente, fkTotem);
            if (!tipoComponente.equals(String.valueOf(TipoEnum.USB))) {
                verificarStatus(valor);
            } else {
                verificarAtivo(valor);
            }
            System.out.println("Captura realizada!");
        } catch (Exception e) {
            Logger.logInfo(String.format("Falha na inserção de captura - %s", e), Componente.class);
            e.printStackTrace();
        }

        try {
            con.update("INSERT INTO captura (valor, tipo, dataHora, fkComponente, fkTotem) VALUES (?,?,?,?,1)",
                    valor, tipoCaptura, LocalDateTime.now(zoneSaoPaulo), idComponente);
        } catch (Exception e) {
            Logger.logInfo(String.format("Falha na inserção de captura (MySQL Local) - %s", e), Componente.class);
            e.printStackTrace();
        }

    }

    protected void inserirCapturaComponente(Long valor, String tipoCaptura) {
        try {
            conSqlServer.update("INSERT INTO captura (valor, tipo, dataHora, fkComponente, fkTotem) VALUES (?,?,?,?,?)",
                    valor, tipoCaptura, LocalDateTime.now(zoneSaoPaulo), idComponente, fkTotem);
            verificarStatus(Double.valueOf(valor));
            System.out.println("Captura realizada!");
        } catch (Exception e) {
            Logger.logInfo(String.format("Falha na inserção de captura - %s", e), Componente.class);
            e.printStackTrace();
        }

        try {
            con.update("INSERT INTO captura (valor, tipo, dataHora, fkComponente, fkTotem) VALUES (?,?,?,?,1)",
                    valor, tipoCaptura, LocalDateTime.now(zoneSaoPaulo), idComponente);
        } catch (Exception e) {
            Logger.logInfo(String.format("Falha na inserção de captura (MySQL Local) - %s", e), Componente.class);
            e.printStackTrace();
        }
    }

    private void verificarStatus(Double valor) {
        try {
            ParametroAlerta parametroAlerta = conSqlServer.queryForObject("SELECT * FROM parametroAlerta WHERE componente = ? and fkEmpresa = ?",
                    new BeanPropertyRowMapper<>(ParametroAlerta.class), tipoComponente, fkEmpresa);

            String nomeTotem = conSqlServer.queryForObject(
                    "SELECT nome FROM totem WHERE idTotem = ?",
                    String.class, fkTotem
            );

            if (valor >= parametroAlerta.getNotificacao()) {
                if (valor > parametroAlerta.getIdeal() && valor <= parametroAlerta.getAlerta()) {
                    this.status = String.valueOf(ParametroAlertaEnum.ALERTA);
                    qtdeAlerta++;
                    if (ultimaCaptura != null && ultimaCaptura > parametroAlerta.getIdeal() && ultimaCaptura <= parametroAlerta.getAlerta()) {
                        if (qtdeAlerta >= OCORRENCIA_ALERTA) {
                            enviarNotificacao(nomeTotem);
                            qtdeAlerta = 0;
                        }
                    }
                } else if (valor > parametroAlerta.getAlerta()) {
                    this.status = String.valueOf(ParametroAlertaEnum.CRITICO);
                    qtdeCritico++;
                    if (ultimaCaptura != null && ultimaCaptura > parametroAlerta.getAlerta()) {
                        if (qtdeCritico >= OCORRENCIA_ALERTA) {
                            enviarNotificacao(nomeTotem);
                            qtdeCritico = 0;
                        }
                    }
                }
            }
            if (!status.equals(String.valueOf(ParametroAlertaEnum.IDEAL)) && valor <= parametroAlerta.getIdeal()) {
                this.status = String.valueOf(ParametroAlertaEnum.IDEAL);
                qtdeAlerta = 0;
                qtdeCritico = 0;
            }
            ultimaCaptura = valor;
        } catch (Exception e) {
            Logger.logInfo(String.format("Erro ao verificar parâmetros e enviar notificações - %s", e), Componente.class);
            e.printStackTrace();
        }
    }

    private void verificarAtivo(Double valor) {
        try {

            if (!notificado && valor.equals(0.0)) {

                String nomeTotem = con.queryForObject(
                        "SELECT nome FROM totem WHERE idTotem = 1",
                        String.class
                );

                enviarNotificacao(nomeTotem);
                notificado = true;
            } else if (notificado && valor.equals(1.0)) {
                notificado = false;
            }

        } catch (Exception e) {
            Logger.logInfo(String.format("Erro ao verificar status e enviar notificações - %s", e), Notification.class);
            e.printStackTrace();
        }

    }

    private void enviarNotificacao(String nomeTotem) {
        if (!tipoComponente.equals(String.valueOf(TipoEnum.USB))) {
            try {
                Notification.enviarNotificacao(String.format("%s está com %s em %s!", nomeTotem, tipoComponente, status));
            } catch (Exception e) {
                Logger.logInfo(String.format("Notificação de %s não enviada - %s", status, e), Notification.class);
                e.printStackTrace();
            }
        } else {
            try {
                Notification.enviarNotificacao(String.format("%s não está com a maquininha conectada!", nomeTotem));
            } catch (Exception e) {
                Logger.logInfo(String.format("Notificação de %s não enviada - %s", tipoComponente, e), Notification.class);
                e.printStackTrace();
            }
        }
    }

    protected List<Integer> getListaIdComponente() {
        List<Integer> idComponentes = conSqlServer.queryForList("SELECT idComponente FROM componente WHERE tipoComponente = ? AND fkTotem = ?",
              Integer.class, tipoComponente, fkTotem);
        return idComponentes;
    }

    protected Integer setIdComponenteTotemValidado() {
        try {
            idComponente = conSqlServer.queryForObject(
                  "SELECT idComponente FROM componente WHERE tipoComponente = ? AND fkTotem = ?",
                  Integer.class, tipoComponente, fkTotem
            );
            return idComponente;
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

    public String searchNomeComponente() {
        try {
            nomeComponente = conSqlServer.queryForObject("SELECT nomeComponente FROM componente WHERE tipoComponente = ? AND fkTotem = ?",
                  String.class, tipoComponente, fkTotem);
            return nomeComponente;
        } catch (EmptyResultDataAccessException e) {
            // Se resultado vazio definir uma mensagem de log ou lançar uma exceção
            Logger.logInfo("Componente não encontrado" + e, UsbT.class);
            throw new RuntimeException("Componente não encontrado para o tipo: " + tipoComponente);
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

    public Integer getFkEmpresa() {
        return fkEmpresa;
    }

    public void setFkEmpresa(Integer fkEmpresa) {
        this.fkEmpresa = fkEmpresa;
    }
}
