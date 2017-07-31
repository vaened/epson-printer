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
     * Center text.
     */
    public static final String CENTER = "\u001b|cA";
    
    /**
     * Write a command.
     */
    public static final String HEX = "\u001b|";

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
