package addressbook.resources.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Giuseppe Serra
 */
public class EMailValidator {
    
    private Pattern pattern;
    private Matcher matcher;
    
    private static final String EMAIL_PATTERN =
                        "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    
    /**
     * Create a Costructor.
     */
    public EMailValidator(){
        pattern=Pattern.compile(EMAIL_PATTERN);
    }
    
    /**
     * Valdate String
     * @param string of validate;
     * @return true if streing is valid other false;
     */
    public boolean validate(final String string){
        matcher=pattern.matcher(string);
        return matcher.matches();
    }
    
}
