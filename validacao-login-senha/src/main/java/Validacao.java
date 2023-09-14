public class Validacao {

    String emailBD = "luluzinha@gmail.com";
    String senhaBD = "12345678";

    Boolean validarLogin(String email, String senha) {
        if (email.equals(emailBD) && senha.equals(senhaBD)) {

            return true;

        } else {

            return false;

        }

    }

}