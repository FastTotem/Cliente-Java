import java.util.Scanner;

public class Login {
    public static void main(String[] args) {

        Scanner leitor = new Scanner(System.in);
        Validacao validacao = new Validacao();
        String senha;
        String login;
        Boolean informacoesValidadas;

        System.out.format("""
                *----------*----------*----------*
                     Bem-Vindo ao FastTotem!
                *----------*----------*----------*
                """);

        do {
            System.out.print("Informe seu Login: ");
            login = leitor.nextLine();

            System.out.print("Informe sua Senha: ");
            senha = leitor.nextLine();

            informacoesValidadas = validacao.validarLogin(login, senha);

            if (informacoesValidadas) {

                System.out.print(String.format("""
                        *----------*----------*----------*
                             Login realizado com Sucesso!
                        *----------*----------*----------*
                        """));

            } else {

                System.out.println("Email e/ou senha incorretos!");

            }

        } while (!informacoesValidadas);
    }

}
