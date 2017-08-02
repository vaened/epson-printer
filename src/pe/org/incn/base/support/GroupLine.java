package pe.org.incn.base.support;

/**
 * GroupLine
 *
 * @author enea <enea.so@live.com>
 */
public class GroupLine {

    private final String group;
    private final boolean independentLine;

    /**
     *
     * @param group
     */
    public GroupLine(String group) {
        this.group = group;
        this.independentLine = false;
    }

    /**
     *
     * @param label
     * @param text
     */
    public GroupLine(String label, String text) {
        this.group = label + text;
        this.independentLine = true;
    }

    /**
     * @return the group
     */
    public String getGroup() {
        return group;
    }

    /**
     * @return the independentLine
     */
    public boolean isIndependentLine() {
        return independentLine;
    }
    
}
