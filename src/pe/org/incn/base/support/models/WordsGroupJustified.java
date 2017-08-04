package pe.org.incn.base.support.models;

import pe.org.incn.main.Configuration;
import pe.org.incn.support.Helpers;

/**
 * WordsGroupJustified
 *
 * @author enea <enea.so@live.com>
 */
public class WordsGroupJustified extends BaseWordsGroup {

    /**
     * WordsGroupJustified constructor.
     *
     * @param label
     * @param text
     * @param separator
     */
    public WordsGroupJustified(String label, String text, String separator) {
        super(label, text);
        SEPARATOR = separator;
        PADDING = "";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean allWidth() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCleanTextWithSpaces() {
        String spaces = Helpers.rightAutocomplete("", this.getWidthPerWord() - this.getCleanTextLength());
        return spaces.concat(this.getCleanText());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValidLength() {
        return Configuration.getCanvasMaxWidth() >= this.getTotalLengthWithSpaces();
    }
}
