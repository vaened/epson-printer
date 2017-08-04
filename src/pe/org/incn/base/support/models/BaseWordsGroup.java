package pe.org.incn.base.support.models;

import pe.org.incn.base.Command;
import pe.org.incn.main.Configuration;
import pe.org.incn.support.Helpers;

/**
 * BaseWordsGroup
 *
 * @author enea <enea.so@live.com>
 */
public abstract class BaseWordsGroup {

    protected final String label;

    protected final String text;

    /**
     * Separator between the label and the text.
     *
     */
    protected String SEPARATOR = ":";

    /**
     * Padding left for the text.
     */
    protected String PADDING = " ";

    /**
     * WordsGroup Constructor.
     *
     * @param label
     * @param text
     */
    public BaseWordsGroup(String label, String text) {
        this.label = label;
        this.text = text;
    }

    /**
     * Set max width per line
     *
     * @return
     */
    public boolean allWidth() {
        return false;
    }

    /**
     * Set max width per line
     *
     * @return
     */
    public final int getWidthPerWord() {
        return Configuration.getCanvasMaxWidth() / 2;
    }

    /**
     * @return the label
     */
    public String getPlainLabel() {
        return this.label;
    }

    /**
     * @return the label
     */
    public String getCleanLabel() {
        return this.getPlainLabel().concat(SEPARATOR);
    }

    /**
     * Returns the text with blank spaces.
     *
     * @return
     */
    public String getCleanLabelWithSpaces() {
        String spaces = Helpers.rightAutocomplete("", this.getWidthPerWord() - this.getCleanLabelLength());
        return this.getCleanLabel().concat(spaces);
    }

    /**
     * Returns the label length
     *
     * @return
     */
    public int getCleanLabelLength() {
        return this.getCleanLabel().length();
    }

    /**
     * Returns the label with commands.
     *
     * @return the label
     */
    public String getBuiltLabel() {
        return Command.prepare(this.getCleanLabelWithSpaces(), Command.BOLD);
    }

    /**
     * @return the text
     */
    public String getPlainText() {
        return this.text;
    }

    /**
     * @return the text
     */
    public String getCleanText() {
        return PADDING.concat(this.getPlainText());
    }

    /**
     * Returns the text with blank spaces.
     *
     * @return
     */
    public String getCleanTextWithSpaces() {
        String spaces = Helpers.rightAutocomplete("", this.getWidthPerWord() - this.getCleanTextLength());
        return this.getCleanText().concat(spaces);
    }

    /**
     * Returns the text length
     *
     * @return
     */
    public int getCleanTextLength() {
        return this.getCleanText().length();
    }

    /**
     * Returns the text with commands.
     *
     * @return
     */
    public String getBuiltText() {
        return Command.prepare(this.getCleanTextWithSpaces(), Command.NORMAL);
    }

    /**
     * Clean length.
     *
     * @return
     */
    public int getCleanLength() {
        return this.getCleanLabelLength() + this.getCleanTextLength();
    }

    /**
     * Total length.
     *
     * @return
     */
    public int getTotalLengthWithSpaces() {
        return this.getCleanLabelWithSpaces().length() + this.getCleanTextWithSpaces().length();
    }

    /**
     * Returns true if the total size of the group fits the size of the canvas.
     *
     * @return
     */
    public boolean isValidLength() {
        return this.getWidthPerWord() >= this.getTotalLengthWithSpaces();
    }
}
