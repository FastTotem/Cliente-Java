import com.github.britooo.looca.api.group.dispositivos.DispositivosUsbGrupo;
import conexao.Conexao;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Totem {

    private Integer idTotem;
    private String nome;
    private String chaveDeAcesso;
    private Integer fkEmpresa;
    private String boardSerialNumber;
    private List<Componente> componentes;
    private final Conexao conexao = new Conexao();
    private final JdbcTemplate con = conexao.getConexaoDoBanco();
    private final JdbcTemplate conSqlServer = conexao.getConexaoSqlServer();

    public Totem() {
        this.componentes = new ArrayList<>();
    }

    public Totem getTotem() {

        try {
            Totem totem = conSqlServer.queryForObject("SELECT * FROM totem WHERE chaveDeAcesso = ?",
                  new BeanPropertyRowMapper<>(Totem.class), chaveDeAcesso);

            return totem;

        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException dataAccessException) {
            Logger.logSevere(String.format("Erro de Conexão - %s", dataAccessException.toString()), Totem.class);
            throw dataAccessException;
        }

    }

    public Totem validarTotemJaAtivo() {

        if (boardSerialNumber.equals("unknown")) {
            System.out.println("Verificamos que você está utilizando uma EC2");
            return null;
        } else {
            try {
                Totem totem = conSqlServer.queryForObject("SELECT * FROM totem WHERE boardSerialNumber = ?",
                      new BeanPropertyRowMapper<>(Totem.class), boardSerialNumber);

                return totem;

            } catch (EmptyResultDataAccessException e) {
                return null;
            } catch (DataAccessException dataAccessException) {
                Logger.logSevere(String.format("Erro de Conexão - %s", dataAccessException.toString()), Totem.class);
                dataAccessException.printStackTrace();
                try {
                    Totem totem = con.queryForObject("SELECT * FROM totem WHERE boardSerialNumber = ?",
                            new BeanPropertyRowMapper<>(Totem.class), boardSerialNumber);

                    return totem;
                } catch (EmptyResultDataAccessException e) {
                    return null;
                }
            }
        }
    }

    public void inserirBoardSerialNumber() {
        try {
            conSqlServer.update("UPDATE totem SET boardSerialNumber = ? WHERE idTotem = ?", boardSerialNumber, idTotem);
            con.update("UPDATE totem SET boardSerialNumber = ? WHERE idTotem = 1", boardSerialNumber);
        } catch (Exception e) {
            Logger.logInfo(String.format("Erro ao inserir boardSerialNumber - %s", e), Totem.class);
            e.printStackTrace();
        }
    }

    public void inserirTotem() {
        try {
            con.update("INSERT INTO totem (nome, chaveDeAcesso) VALUES (?,?);",
                    nome, chaveDeAcesso);
        } catch (Exception e) {
            Logger.logWarning(String.format("Erro ao inserir totem na conexão MySQL - %s", e), Totem.class);
            e.printStackTrace();
        }
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

    public List<Componente> getComponentes() {
        return componentes;
    }

    public void setComponentes(List<Componente> componentes) {
        this.componentes = componentes;
    }
}
