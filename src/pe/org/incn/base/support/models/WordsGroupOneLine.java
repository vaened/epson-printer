package pe.org.incn.base.support.models;

/**
 * WordsGroupOneLine
 *
 * @author enea <enea.so@live.com>
 */
public class WordsGroupOneLine extends BaseWordsGroup {

    /**
     * WordsGroupOneLine constructor.
     *
     * @param label
     * @param text
     */
    public WordsGroupOneLine(String label, String text) {
        super(label, text);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isOneLine() {
        return false;
    }
}
