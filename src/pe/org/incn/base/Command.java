package pe.org.incn.base;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import pe.org.incn.support.Helpers;

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
    public static final String NORMAL = "\u001b|N";

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
     * @param text
     * @param commands
     * @return
     */
    public static String prepare(String text, String... commands) {
        List<String> listCommands = Arrays.asList(commands).stream().distinct().map(Object::toString).collect(Collectors.toList());
        boolean WRITE_BLANK_LINE;

        if (WRITE_BLANK_LINE = listCommands.contains(BLANK_LINE)) {
            listCommands.remove(BLANK_LINE);
        }

        String command = String.join("", listCommands);
        text = Helpers.concat(command, text);

        if (WRITE_BLANK_LINE) {
            text += BLANK_LINE;
        }

        return text;
    }
}
