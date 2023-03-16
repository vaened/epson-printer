package pe.org.incn.support.tables;

import pe.org.incn.main.Configuration;
import pe.org.incn.support.Helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ColumnsFormatter {
    private final List<String> columns = new ArrayList<>();

    private int totalLength = 0;


    public String line() {
        return Helpers.concat(this.mainColumn(), this.getAllSecondaryColumns());
    }

    public void push(String content, TableColumn column) {
        this.push(content, column.length(), column.alignment());
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
        StringBuilder box = new StringBuilder();

        for (String column : columns) {
            box.append(column);
        }

        return box.toString();
    }

    private int getSecondaryColumnsSize() {
        return totalLength;
    }

    private String mainColumn() {
        String column = String.valueOf(this.columns.stream().findFirst());
        int maxLength = Configuration.getCanvasMaxWidth() - this.getSecondaryColumnsSize();
        return Helpers.rightAutocomplete(column.trim(), maxLength);
    }

    private String formatSizeColumn(String value, int maxLength) {
        if (value.length() > maxLength) {
            return value.substring(0, maxLength);
        }

        return value;
    }

    private String pad(String value, int length, Alignment alignment) {
        if (Objects.requireNonNull(alignment) == Alignment.RIGHT) {
            return Helpers.leftAutocomplete(value, length);
        }

        return Helpers.rightAutocomplete(value, length);
    }
}
