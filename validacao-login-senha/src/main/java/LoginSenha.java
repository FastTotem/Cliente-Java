import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginSenha {
    private static final String email_regex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    private static final Pattern email_pattern = Pattern.compile(email_regex);

    public static boolean emailValidator(String email) {
        if (email == "") {
            System.out.println(" E-mail precisa ser preenchido");
        }
        Matcher matcher = email_pattern.matcher(email);
        return matcher.matches();
    }


    private static final String senha_regex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,10}$";
    /* ^         # start of the string
 (?=.*\d)        # a digit must occur at least once
 (?=.*[a-z])     # a lower case letter must occur at least once
 (?=.*[A-Z])     # an upper case letter must occur at least once
 .{6,10}          # 6-10 character password, both inclusive
 $               # end of the string*/
    private static final Pattern senha_pattern = Pattern.compile(senha_regex);
    public static boolean validacaoSenha(String senha) {
        if (senha == "") {
            System.out.println(" Senha precisa ser preenchida");
        }
        Matcher matcher = senha_pattern.matcher(senha);
        return matcher.matches();
    }
}
