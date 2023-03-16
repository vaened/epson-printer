package pe.org.incn.support.tables;

public final class ColumnSchema {

    private final String label;
    private final String objectKey;
    private final TableColumn column;

    public ColumnSchema(String label, String objectKey, TableColumn column) {
        this.label = label;
        this.objectKey = objectKey;
        this.column = column;
    }

    public String label() {
        return this.label;
    }

    public String objectKey() {
        return this.objectKey;
    }

    public TableColumn column() {
        return this.column;
    }
}
