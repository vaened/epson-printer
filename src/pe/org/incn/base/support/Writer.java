package pe.org.incn.base.support;

import pe.org.incn.base.support.models.WordsGroupCenter;
import pe.org.incn.base.support.models.WordsGroupJustified;
import pe.org.incn.base.support.models.WordsGroupMultiLine;
import pe.org.incn.base.support.models.WordsGroupOneLine;

/**
 * Writer
 *
 * @author enea <enea.so@live.com>
 */
public class Writer {

    public WordsGroupOneLine groupOneLine(String label, String text) {
        return new WordsGroupOneLine(label, text);
    }

    public WordsGroupMultiLine groupMultiLine(String label, String text) {
        return new WordsGroupMultiLine(label, text);
    }

    public WordsGroupCenter groupCenter(String label, String text) {
        return new WordsGroupCenter(label, text);
    }

    public WordsGroupJustified groupJustified(String label, String text) {
        return new WordsGroupJustified(label, text, ":");
    }

    public WordsGroupJustified groupJustified(String label, String text, String separator) {
        return new WordsGroupJustified(label, text, separator);
    }
}
