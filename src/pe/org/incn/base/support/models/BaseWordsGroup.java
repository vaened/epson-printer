package pe.org.incn.base.support.models;

/**
 * BaseWordsGroup
 *
 * @author enea <enea.so@live.com>
 */
public abstract class BaseWordsGroup {

    protected final String label;

    protected final String text;

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
     * Returns true in case the group is on a line.
     *
     * @return
     */
    public abstract boolean isOneLine();

    /**
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * Returns the label length
     *
     * @return
     */
    public int getLabelLength() {
        return this.getLabel().length();
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * Returns the text length
     *
     * @return
     */
    public int getTextLength() {
        return this.getText().length();
    }

    /**
     * Total length.
     *
     * @return
     */
    public int length() {
        return this.getLabelLength() + this.getTextLength();
    }
}
