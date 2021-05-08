package businessManagement.config;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailConfirmed {
    String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
    Pattern pattern=Pattern.compile(regex);
    public boolean confirmed(String email){
        Matcher matcher=pattern.matcher(email);
        return matcher.matches();
    }
}
