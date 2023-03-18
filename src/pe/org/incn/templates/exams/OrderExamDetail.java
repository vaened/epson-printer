package pe.org.incn.templates.exams;

import pe.org.incn.base.JSONPrintable;
import pe.org.incn.support.tables.*;

import java.util.Arrays;
import java.util.List;

public class OrderExamDetail extends TableBuilder {
    public OrderExamDetail(JSONPrintable printable) {
        super(printable);
    }

    @Override
    public List<ColumnSchema> items() {
        return Arrays.asList(
                new ColumnSchema("CPMS", "code", Columns.CPMS),
                new ColumnSchema("Descripcion", "description", Columns.DESCRIPTION)
        );
    }

    @Override
    public TableColumn mainColumn() {
        return Columns.DESCRIPTION;
    }
}


enum Columns implements TableColumn {

    DESCRIPTION(92, "description", Alignment.LEFT),
    CPMS(8, "code", Alignment.LEFT);

    private final int length;
    private final String key;
    private final Alignment alignment;

    Columns(int length, String key, Alignment alignment) {
        this.length = length;
        this.key = key;
        this.alignment = alignment;
    }

    @Override
    public int length() {
        return this.length;
    }

    @Override
    public String key() {
        return this.key;
    }

    @Override
    public Alignment alignment() {
        return this.alignment;
    }
}