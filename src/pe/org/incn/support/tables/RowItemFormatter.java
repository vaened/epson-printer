package pe.org.incn.support.tables;

import pe.org.incn.main.Configuration;
import pe.org.incn.support.Helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class RowItemFormatter {
    public final List<TableCellSchema> cells = new ArrayList<>();

    private final TableColumn mainColumn;

    public RowItemFormatter(TableColumn mainColumn) {
        this.mainColumn = mainColumn;
    }

    public String line() {
        return this.cells.stream().map(this::rearrangeLengths)
                .collect(Collectors.joining());
    }

    public void push(String content, TableColumn column) {
        this.cells.add(new TableCellSchema(content, column));
    }

    private boolean isSecondaryColumn(TableColumn column) {
        return !column.key().equals(this.mainColumn.key());
    }

    private String rearrangeLengths(TableCellSchema cell) {
        if (this.isSecondaryColumn(cell.column())) {
            return this.resize(cell.content(), cell.column().length(), cell.column().alignment());
        }

        Integer totalLengthOfSecondaryColumns = this.cells.stream()
                .filter(c -> this.isSecondaryColumn(c.column()))
                .mapToInt(c -> c.column().length())
                .sum();

        int maxLength = Configuration.getCanvasMaxWidth() - totalLengthOfSecondaryColumns;

        return this.resize(cell.content(), maxLength, cell.column().alignment());
    }

    private String resize(String content, int length, Alignment alignment) {
        String suitableContent = this.limitCharactersOf(content, length);

        return suitableContent.length() == length
                ? suitableContent
                : this.pad(suitableContent, length, alignment);


    }

    private String limitCharactersOf(String value, int maxLength) {
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
