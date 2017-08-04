package pe.org.incn.base.support.models;

import pe.org.incn.main.Configuration;
import pe.org.incn.support.Helpers;

/**
 * WordsGroupCenter
 *
 * @author enea <enea.so@live.com>
 */
public class WordsGroupCenter extends BaseWordsGroup {

    public WordsGroupCenter(String label, String text) {
        super(label, text);
    }

    @Override
    public boolean allWidth() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCleanLabelWithSpaces() {
        String spaces = Helpers.rightAutocomplete("", this.getWidthPerWord() - this.getCleanLabelLength());
        return spaces.concat(this.getCleanLabel());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValidLength() {
        return Configuration.getCanvasMaxWidth() >= this.getTotalLengthWithSpaces();
    }
}
