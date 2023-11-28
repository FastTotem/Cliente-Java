package conexao;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;


public class Conexao {
    private BasicDataSource dataSource;
    private BasicDataSource dataSourceSqlServer;

    private final JdbcTemplate conexaoDoBanco;
    private final JdbcTemplate conexaoSqlServer;

    public Conexao() {
        dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/fasttotem");
        dataSource.setUsername("fastTotemAdmin");
        dataSource.setPassword("fasttotem123");

        dataSourceSqlServer = new BasicDataSource();
        dataSourceSqlServer.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//        dataSourceSqlServer.setUrl("jdbc:sqlserver://ec2:1433;database=fasttotem;encrypt=true;trustServerCertificate=true;");
        dataSourceSqlServer.setUrl("jdbc:sqlserver://ec2-54-144-141-96.compute-1.amazonaws.com:1433;database=fasttotem;encrypt=true;trustServerCertificate=true;");
        //dataSource.setUrl("jdbc:sqlserver://fasttotem.database.windows.net:1433;database=fasttotem;user=fastTotemAdmin;password=fasttotem123;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;");
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
    public BasicDataSource getBasicDataSource(){
        return dataSource;
    }

}
