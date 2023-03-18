package pe.org.incn.support.tables;

public class TableCellSchema {

    private final String content;
    private final TableColumn column;

    TableCellSchema(String content, TableColumn column) {

        this.content = content;
        this.column = column;
    }

    public String content() {
        return this.content;
    }

    public TableColumn column() {
        return this.column;
    }
}
