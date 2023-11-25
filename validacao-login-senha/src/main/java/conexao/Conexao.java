package conexao;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;


public class Conexao {

    private final JdbcTemplate conexaoDoBanco;
    private final JdbcTemplate conexaoSqlServer;

    public Conexao() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");

        BasicDataSource dataSourceSqlServer = new BasicDataSource();
        dataSourceSqlServer.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

        dataSource.setUrl("jdbc:mysql://ec2-34-226-153-19.compute-1.amazonaws.com:3306/fasttotem");
        dataSource.setUsername("fastTotemAdmin");
        dataSource.setPassword("fasttotem123");

        dataSourceSqlServer.setUrl("jdbc:sqlserver://localhost:1433;database=fasttotem");
        dataSourceSqlServer.setUsername("fastTotemAdmin");
        dataSourceSqlServer.setPassword("fasttotem123");

        conexaoDoBanco = new JdbcTemplate(dataSource);
        conexaoSqlServer = new JdbcTemplate(dataSourceSqlServer);
    }

    public JdbcTemplate getConexaoDoBanco() {
        return conexaoDoBanco;
    }

    public JdbcTemplate getConexaoSqlServer() {
        return conexaoSqlServer;
    }

}
