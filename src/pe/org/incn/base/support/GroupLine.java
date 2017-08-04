package pe.org.incn.base.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import pe.org.incn.base.support.models.BaseWordsGroup;
import pe.org.incn.main.Configuration;
import pe.org.incn.support.Helpers;

/**
 * GroupLine
 *
 * @author enea <enea.so@live.com>
 */
public class GroupLine {

    private final Stream<BaseWordsGroup> group;

    /**
     * GroupLine constructor.
     *
     * @param group
     */
    public GroupLine(Stream<BaseWordsGroup> group) {
        this.group = group;
    }

    /**
     *
     * @return
     */
    public String make() {
        Helpers.printRules();
        List<String> lines = new ArrayList<>();
        List<BaseWordsGroup> groups = this.group.collect(Collectors.toList());

        Collections.sort(groups, (BaseWordsGroup o1, BaseWordsGroup o2) -> {
            return Boolean.compare(!o1.allWidth(), !o2.allWidth());
        });

        lines.addAll(this.buildLines(Helpers.divideList(groups, 2)));

        return String.join("", lines);
    }

    /**
     *
     * @param groupList
     * @return
     */
    protected List<String> buildLines(List<List<BaseWordsGroup>> groupList) {
        List<String> lines = new ArrayList<>();

        groupList.forEach(groups -> {
            BaseWordsGroup group = groups.get(0);
            String firstLine = this.buildFirstLine(group);
            String lastLine = this.buildLastLine(group);

            if (groups.size() == 2) {
                group = groups.get(1);
                firstLine += this.buildFirstLine(group);
                lastLine += this.buildLastLine(group);
            }

            lines.add(firstLine);

            if (!lastLine.trim().equals("")) {
                lines.add(lastLine);
            }

        });

        return lines;
    }

    /**
     * Built the first line in the document.
     *
     * @param group
     * @return
     */
    protected String buildFirstLine(BaseWordsGroup group) {
        if (this.isFullWidth(group)) {
            return group.getBuiltLabel() + group.getBuiltText();
        }

        return group.getBuiltLabel();
    }
    
    /**
     * Built the second line in the document.
     *
     * @param group
     * @return
     */
    protected String buildLastLine(BaseWordsGroup group) {
        if (this.isFullWidth(group)) {
            return Helpers.rightAutocomplete("", this.getMiddleWidth());
        }

        return group.getBuiltText();
    }

    /**
     * returns true is occupied full line
     *
     * @param group
     * @return
     */
    protected boolean isFullWidth(BaseWordsGroup group) {
        return group.allWidth() || group.getTotalLengthWithSpaces() <= this.getMiddleWidth();
    }

    /**
     *
     * @return
     */
    protected int getMiddleWidth() {
        return Configuration.getCanvasMaxWidth() / 2;
    }
}
