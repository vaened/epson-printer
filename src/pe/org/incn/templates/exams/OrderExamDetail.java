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
                new ColumnSchema("Descripcion", "description", Columns.DESCRIPTION),
                new ColumnSchema("CPMS", "code", Columns.CPMS)
        );
    }
}


enum Columns implements TableColumn {

    DESCRIPTION(92, Alignment.LEFT),
    CPMS(8, Alignment.RIGHT);

    private final int length;
    private final Alignment alignment;

    Columns(int length, Alignment alignment) {
        this.length = length;
        this.alignment = alignment;
    }

    @Override
    public int length() {
        return this.length;
    }

    @Override
    public Alignment alignment() {
        return this.alignment;
    }
}