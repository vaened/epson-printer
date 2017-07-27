package pe.org.incn.base;

/**
 * Command
 *
 * @author enea <enea.so@live.com>
 */
public class Command {
    
    /**
     * Break line.
     */
    public static final String BREAK = "uF";
    
    /**
     * Inser black line.
     */
    public static final String BLANK_LINE = "\n";
    
    /**
     * Bold text.
     */
    public static final String BOLD = "\u001b|bC";
    
    /**
     * Center text.
     */
    public static final String CENTER = "\u001b|cA";
    
    public static final String HEX = "\u001b|";
    
    
    /**
     * Convert a text to bold.
     *
     * @param commands
     * @return
     */
    public static String prepare(String[] commands) {
        String join = String.join("", commands);
        return Command.HEX.concat(join);
    }
}
