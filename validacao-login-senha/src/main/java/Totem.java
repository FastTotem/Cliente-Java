import conexao.Conexao;
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

    public Totem() {
        this.componentes = new ArrayList<>();
    }

    public Totem(Integer idTotem, String nome, String chaveDeAcesso, Integer fkEmpresa, String boardSerialNumber) {
        this.idTotem = idTotem;
        this.nome = nome;
        this.chaveDeAcesso = chaveDeAcesso;
        this.fkEmpresa = fkEmpresa;
        this.boardSerialNumber = boardSerialNumber;
    }

    public Totem getTotem() {
        String query = (boardSerialNumber.equals("unknown")) ?
              "SELECT * FROM totem WHERE chaveDeAcesso = ?" :
              "SELECT * FROM totem WHERE boardSerialNumber = ?";

        try {
            return con.queryForObject(query,
                  new BeanPropertyRowMapper<>(Totem.class), (boardSerialNumber.equals("unknown")) ? chaveDeAcesso : boardSerialNumber);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Totem validarTotemJaAtivo() {
        Scanner in = new Scanner(System.in);
        if (boardSerialNumber.equals("unknown")) {
            Boolean totemAchado = false;
            Totem totem = null;
            do {
                System.out.println("Verificamos que você está utilizando uma EC2, por favor insira a chave do totem:");
                String chave = in.nextLine();
                try {
                    totem = con.queryForObject("SELECT * FROM totem WHERE chaveDeAcesso = ?",
                          new BeanPropertyRowMapper<>(Totem.class), chave);
                    totemAchado = true;

                } catch (EmptyResultDataAccessException e) {
                    System.out.println("Chave errada! Insira novamente!");
                }
            } while (!totemAchado);
            return totem;
        } else {
            try {
                return con.queryForObject("SELECT * FROM totem WHERE boardSerialNumber = ?",
                      new BeanPropertyRowMapper<>(Totem.class), boardSerialNumber);

            } catch (EmptyResultDataAccessException e) {
                return null;
            }
        }
    }

    public void inserirBoardSerialNumber() {
        con.update("UPDATE totem SET boardSerialNumber = ? WHERE idTotem = ?", boardSerialNumber, idTotem);
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

    public Conexao getConexao() {
        return conexao;
    }

    public JdbcTemplate getCon() {
        return con;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID do Totem: ").append(idTotem).append("\n");
        sb.append("Nome: ").append(nome).append("\n");
        sb.append("Chave de Acesso: ").append(chaveDeAcesso).append("\n");
        sb.append("ID da Empresa: ").append(fkEmpresa).append("\n");
        sb.append("Número de Série da Placa: ").append(boardSerialNumber).append("\n");
        sb.append("Componentes:\n");
        for (Componente componente : componentes) {
            sb.append(componente.toString()).append("\n");
        }
        return sb.toString();
    }
}
