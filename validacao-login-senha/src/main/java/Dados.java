import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.discos.Disco;
import com.github.britooo.looca.api.group.discos.DiscoGrupo;
import com.github.britooo.looca.api.group.memoria.Memoria;
import com.github.britooo.looca.api.group.processador.Processador;
import conexao.Conexao;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Scanner;

public class Dados {
    public static void main(String[] args) {
        Conexao conexao = new Conexao();
        Looca looca = new Looca();
        JdbcTemplate con = conexao.getConexaoDoBanco();
        Scanner leitor = new Scanner(System.in);
        Cliente cliente = new Cliente();
        String email = "";
        String senha = "";
        Integer opcao = 0;
        double tamanhoTotalGiB = 0;
        Double tamanhoDisco = 0.0;

        System.out.println("Bem vindo ao sistema de monitoramento FASTTOTEM" +
              "\nVocê tem cadastro? Se sim, digite 1: \n" +
              "Se não digite 2 e faça o cadastro");
        opcao = leitor.nextInt();

        leitor.nextLine();
        if (opcao == 1) {
            System.out.println("Digite seu email:");
            email = leitor.nextLine();
            cliente.setEmail(email);

            System.out.println("Digite sua senha agora:");
            senha = leitor.nextLine();
            cliente.setSenha(senha);

            String sql = "SELECT * FROM usuario WHERE email = ? AND senha = ?";

            try {
                List<Cliente> listaCliente = con.query(sql, new Object[]{cliente.getEmail(), cliente.getSenha()}, new BeanPropertyRowMapper<>(Cliente.class));

                if (listaCliente.isEmpty()) {
                    System.out.println("Não existe cadastro em nossa base de dados, faça o cadastro e tente novamente");
                } else {

                    do {
                        System.out.println("--------------------------" +
                              "\nVerifiquei que você tem cadastro, o que você quer visualizar\n" +
                              "1-)Porcentagem de Uso do Disco\n" +
                              "2-)Quantidade de ram\n" +
                              "3-)Tamanho total do disco:\n" +
                              "4-)Memória Disponível\n" +
                              "5-)Porcentagem de Uso da CPU:\n" +
                              "6-)Porcentagem de uso do disco: \n" +
                              "8-)Sair" +
                              "--------------------------");
                        opcao = leitor.nextInt();

                        switch (opcao) {
                            case 1: {
                                Processador processador = new Processador();
                                System.out.println(processador);
                                Double totalUsoProcessador = processador.getUso();
                                BigDecimal porcentagemUsoDisco = BigDecimal.valueOf(totalUsoProcessador).setScale(2, RoundingMode.HALF_UP);
                                System.out.println(porcentagemUsoDisco);
                                break;
                            }
                            case 2: {
                                Memoria memoria = new Memoria();
                                Double ramDisponivel = (double) memoria.getDisponivel() / (1024 * 1024 * 1024);
                                System.out.println(
                                      String.format("""
                                            Memória Ram disponível: %.2f                                          
                                            """, ramDisponivel));
                                System.out.println(ramDisponivel);
                                break;
                            }
                            case 3: {
                                DiscoGrupo discoGrupo = new DiscoGrupo();
                                System.out.println(
                                      String.format("""
                                            A quantidade total de disco é de %d
                                            """, discoGrupo.getTamanhoTotal())
                                );
                                break;
                            }
                            case 4: {
                                MemoriaT memoria = new MemoriaT();
                                Double memoriaDisponivel = memoria.getDisponivel() / (1024 * 1024 * 1024.0);
                                memoriaDisponivel = Math.round(memoriaDisponivel * 100.0) / 100.0;
                                System.out.println(
                                      String.format("Memória em Uso: %.2f" +
                                            "Memória atualmente disponível: %.2f", memoria.getPorcentagemEmUso(),memoriaDisponivel)
                                );
                                break;
                            }
                            case 5: {
                                ProcessadorT processadorT = new ProcessadorT();
                                Processador processador = new Processador();
                                Double porcentagemUsoCpu = processadorT.getEmUso() / processador.getNumeroCpusFisicas();
                                BigDecimal usoCpuPorcentagem = BigDecimal.valueOf(porcentagemUsoCpu).setScale(2, RoundingMode.HALF_UP);
                                System.out.println(
                                      String.format("Porcentagem de uso da cpu %.2f", usoCpuPorcentagem));
                                break;
                            }
                            case 6: {

                                for (Disco disco : looca.getGrupoDeDiscos().getDiscos()) {
                                    tamanhoTotalGiB = (double) disco.getTamanho() / (1024 * 1024 * 1024);
                                    double totalDisco = Math.round(tamanhoTotalGiB * 100.0) / 100.0;
                                   tamanhoDisco = Double.valueOf(disco.getTamanho());
                                    Double ttotalDisco = (double) Math.round(tamanhoTotalGiB);


                                }
                                BigDecimal tamanhoDisponivelDoDisco = new BigDecimal(tamanhoDisco)
                                      .setScale(2, RoundingMode.HALF_UP)
                                      .divide(new BigDecimal(1024 * 1024 * 1024), 2, RoundingMode.HALF_UP);

                                System.out.println(
                                      String.format("Tamanho total do disco : %.2f", tamanhoDisponivelDoDisco));
                                break;
                            }
                        }
                    } while (!opcao.equals(8));
                }
            } catch (Exception erro) {
                erro.printStackTrace();
            }

        } else {
            System.out.println("Vamos ao cadastro!!");
            System.out.println("Nome:");
            String nome = leitor.nextLine();

            System.out.println("E-mail");
            email = leitor.nextLine();

            System.out.println("Crie uma senha");
            do {
                senha = leitor.nextLine();
                if (senha.length() < 8) {
                    System.out.println("Senha muito curta, tente novamente");
                }
            } while (senha.length() < 8);

            System.out.println("Fazendo o cadastro ...");
            try {
                con.update("insert into usuario (nome , email , senha) values (? , ? ,?)", nome, email, senha);

                System.out.println("Cadastro realizado com sucesso");
            } catch (Exception erro) {
                erro.printStackTrace();
            }
        }
    }
}
