package pe.org.incn.templates.cashbox.useful;

import java.util.ArrayList;
import java.util.List;
import pe.org.incn.main.Configuration;
import pe.org.incn.support.Helpers;

/**
 * ColumnsFormatter
 *
 * @author enea <enea.so@live.com>
 */
public class ColumnsFormatter {

    private final List<String> columns = new ArrayList<>();

    private int totalLength = 0;

    private final String mainColumn;

    public enum Alignment {
        LEFT, RIGTH,
    }

    public ColumnsFormatter(String mainColumn) {
        this.mainColumn = mainColumn;
    }

    public static ColumnsFormatter make(String mainColumn) {
        return new ColumnsFormatter(mainColumn);
    }

    public String getRow() {
        return Helpers.concat(this.getMainColumn(), this.getAllSecondaryColumns());
    }

    public void push(String content, int length) {
        this.push(content, length, Alignment.LEFT);
    }

    public void push(String content, int length, Alignment alignment) {
        this.totalLength += length;

        if (totalLength < Configuration.getCanvasMaxWidth()) {
            /// throw exception
        }

        content = this.formatSizeColumn(content, length);
        this.columns.add(this.pad(content, length, alignment));
    }

    private String getAllSecondaryColumns() {
        String box = "";

        for (String column : columns) {
            box += column;
        }

        return box;
    }

    private int getSecondaryColumnsSize() {
        return totalLength;
    }

    private String getMainColumn() {
        int maxLength = Configuration.getCanvasMaxWidth() - this.getSecondaryColumnsSize();
        return Helpers.rightAutocomplete(this.mainColumn, maxLength);
    }

    private String formatSizeColumn(String value, int maxLength) {
        if (value.length() > maxLength) {
            return value.substring(0, maxLength);
        }

        return value;
    }

    private String pad(String value, int length, Alignment alignment) {
        switch (alignment) {
            case RIGTH:
                return Helpers.leftAutocomplete(value, length);
        }

        return Helpers.rightAutocomplete(value, length);
    }
}
