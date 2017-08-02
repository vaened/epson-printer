package pe.org.incn.base.support.models;

/**
 * WordsGroupMultiLine
 *
 * @author enea <enea.so@live.com>
 */
public class WordsGroupMultiLine extends BaseWordsGroup {

    /**
     * WordsGroup constructor.
     *
     * @param label
     * @param text
     */
    public WordsGroupMultiLine(String label, String text) {
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
