package pe.org.incn.base.support.models;

import pe.org.incn.main.Configuration;

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
     * {@inheritDoc }
     */
    @Override
    public boolean isValidLength() {
        return (Configuration.getCanvasMaxWidth() / 2 )>= Math.max(this.getCleanLabelLength(), this.getCleanTextLength());
    }
}
