package pe.org.incn.base.support.models;

import pe.org.incn.support.Helpers;

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
    public String getCleanLabelWithSpaces() {
        return this.getCleanLabel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCleanTextWithSpaces() {
        String spaces = Helpers.rightAutocomplete("", this.getWidthPerWord() - this.getCleanLength());
        return this.getCleanText().concat(spaces);
    }
}
