package pe.org.incn.base.support;

import pe.org.incn.base.Command;
import pe.org.incn.support.Helpers;

/**
 * GroupFormatter
 *
 * @author enea <enea.so@live.com>
 */
public class GroupFormatter {

    private String label;

    private String text;

    public GroupFormatter() {
    }

    public static GroupFormatter instance() {
        return new GroupFormatter();
    }

    public static GroupFormatter instance(String label, String text) {
        return new GroupFormatter(label, text);
    }

    /**
     * MoneyFormatter constructor.
     *
     * @param label
     * @param text
     */
    public GroupFormatter(String label, String text) {
        this.setLabel(label);
        this.setText(text);
    }

    /**
     * @param label the label to set
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    public String getCleanLabel() {
        return Helpers.concat(this.label, ": ");
    }

    /**
     * @return the clean text
     */
    public String getCleanText() {
        return this.text;
    }

    /**
     * @return the total size
     */
    public Integer getTotalSize() {
        return Helpers.concat(this.getCleanLabel(), this.getCleanText()).length();
    }

    /**
     * @param commands
     * @return the built label
     */
    public String getBuiltLabel(String... commands) {
        return Command.prepare(this.getCleanLabel(), this.concatCommands(commands, Command.BOLD));
    }

    /**
     * @param commands
     * @return the built text
     */
    public String getBuiltText(String... commands) {
        return Command.prepare(this.getCleanText(), this.concatCommands(commands, Command.NORMAL));
    }

    /**
     * @return the built line
     */
    public String makeWithoutPadding() {
        return Helpers.concat(this.getBuiltLabel(), this.getBuiltText());
    }

    /**
     * @return the built line
     */
    public String makeSpaceBetween() {
        return Helpers.concat(this.getBuiltLabel(), this.getBuiltText(Command.RIGHT));
    }

    protected String[] concatCommands(String[] overrides, String... commands) {
        return Helpers.concat(commands, overrides);
    }
}
