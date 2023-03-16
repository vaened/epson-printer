package pe.org.incn.support.tables;

import jpos.JposException;
import pe.org.incn.base.JSONPrintable;
import pe.org.incn.json.JSONArray;
import pe.org.incn.json.JSONObject;

import java.util.List;

public abstract class TableBuilder {


    private final JSONPrintable printable;

    public TableBuilder(JSONPrintable printable) {
        this.printable = printable;
    }

    public abstract List<ColumnSchema> items();

    public void draw(JSONArray array) throws JposException {
        this.printable.replicate('-');

        this.printHeader();

        this.printable.replicate('-');

        for (JSONObject json : array) {
            this.printLine(json);
        }

        this.printable.replicate('-');
    }

    private void printHeader() throws JposException {
        ColumnsFormatter formatter = new ColumnsFormatter();
        items().forEach(item -> formatter.push(item.label(), item.column()));

        this.printable.getWriter().writeBoldLine(formatter.line());
    }

    private void printLine(JSONObject object) throws JposException {
        ColumnsFormatter formatter = new ColumnsFormatter();
        items().forEach(item -> formatter.push(object.json(item.objectKey()), item.column()));

        this.printable.getWriter().writeBoldLine(formatter.line());
    }
}
