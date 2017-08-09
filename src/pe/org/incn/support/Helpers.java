package pe.org.incn.support;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import pe.org.incn.main.Configuration;

/**
 * Helpers
 *
 * @author enea <enea.so@live.com>
 */
public class Helpers {

    /**
     * Returns true if it finds an element within a string array.
     *
     * @param elements
     * @param value
     * @return
     */
    public static boolean existInStringArray(String[] elements, String value) {
        for (String element : elements) {
            if (element.equals(value)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Combine multiple strings into one.
     *
     * @param elements
     * @return
     */
    public static String concat(String... elements) {
        String out = "";

        for (String element : elements) {
            out += element;
        }

        return out;
    }

    /**
     * Right autocomplete.
     *
     * @param text
     * @param quantity
     * @return
     */
    public static String rightAutocomplete(String text, Integer quantity) {
        if (quantity > 0) {
            String exp = concat("%-", quantity.toString(), "s");
            return String.format(exp, text);
        }

        return text;
    }

    public static void printRules() {
        String line = "";
        int max = Configuration.getCanvasMaxWidth() - 2;

        for (int i = 0; i < max / 2; i++) {
            line += "-";
        }

        line += "||";

        for (int i = 0; i < max / 2; i++) {
            line += "-";
        }

        System.out.println(line);
    }

    /**
     * Split a list into multiple lists.
     *
     * @param <T>
     * @param source
     * @param chunksize
     * @return
     */
    public static <T> List<List<T>> divideList(List<T> source, int chunksize) {
        List<List<T>> result = new ArrayList<>();
        int start = 0;
        while (start < source.size()) {
            int end = Math.min(source.size(), start + chunksize);
            result.add(source.subList(start, end));
            start += chunksize;
        }

        return result;
    }

    public static URL resource(String filename) {
        return ClassLoader.getSystemResource("pe/org/incn/resources/".concat(filename));
    }
}
