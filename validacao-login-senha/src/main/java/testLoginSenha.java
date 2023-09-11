import java.util.Scanner;

public class testLoginSenha {
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        loginSenha validacao = new loginSenha();
        String senha;
        String login;

        do {
            System.out.print("Informe seu Login: ");
            login = entrada.nextLine();

            System.out.print("Informe sua Senha: ");
            senha = entrada.nextLine();

            if (loginSenha.emailValidator(login) && (loginSenha.validacaoSenha(senha))) {
                System.out.println("*----------*----------*----------* \n" +
                      "   Login realizado com Sucesso! \n" +
                      "*----------*----------*----------*");
            } else if (loginSenha.emailValidator(login) != true) {
                System.out.println(" E-mail inválido \n Tente Novamente");
            } else if (loginSenha.validacaoSenha(senha) != true){
                System.out.println(" Senha inválida \n Tente Novamente");
            }
        }
        while (loginSenha.emailValidator(login) != true || loginSenha.validacaoSenha(senha) != true);
    }

}
