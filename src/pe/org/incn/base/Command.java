package pe.org.incn.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
     * Underline text.
     */
    public static final String UNDERLINE = "\u001b|uC";

    /**
     * Center text.
     */
    public static final String CENTER = "\u001b|cA";
    
    /**
     * Right text.
     */
    public static final String RIGHT = "\u001b|rA";
    
    /**
     * Write a command.
     */
    public static final String HEX = "\u001b|";

    /**
     * Normal size text.
     */
    public static final String NORMAL_SIZE = "\u001b|1C";

    /**
     * Medium-sized text
     */
    public static final String MEDIUM_SIZE = "\u001b|2C";

    /**
     * Large size text
     */
    public static final String LARGE_SIZE = "\u001b|4C";

    /**
     * Convert a text to bold.
     *
     * @param commands
     * @return
     */
    public static String prepare(String[] commands) {
        List<String> list = new ArrayList(Arrays.asList(commands));

        if (list.contains(BLANK_LINE)) {
            list.remove(BLANK_LINE);
            list.add(BLANK_LINE);
        }

        String join = String.join("", commands);
        return Command.HEX.concat(join);
    }
}
