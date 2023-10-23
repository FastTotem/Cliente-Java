package conexao;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class Conexao {

    private final JdbcTemplate conexaoDoBanco;

    public Conexao() {
        BasicDataSource dataSource = new BasicDataSource();

        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");

        dataSource.setUrl("jdbc:mysql://localhost:3306/fastTotem");
        dataSource.setUsername("fastTotemAdmin");
        dataSource.setPassword("fasttotem123");

        conexaoDoBanco = new JdbcTemplate(dataSource);
    }

    public JdbcTemplate getConexaoDoBanco() {
        return conexaoDoBanco;
    }

}
