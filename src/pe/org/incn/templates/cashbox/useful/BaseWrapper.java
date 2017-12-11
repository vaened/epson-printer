package pe.org.incn.templates.cashbox.useful;

import jpos.JposException;
import pe.org.incn.base.JSONPrintable;
import pe.org.incn.base.WriterContract;
import pe.org.incn.json.JSONArray;
import pe.org.incn.json.JSONObject;

/**
 * BaseWrapper
 *
 * @author enea <enea.so@live.com>
 */
public abstract class BaseWrapper {

    private final JSONPrintable printable;
    private final ColumnsFormatter formatter;

    public BaseWrapper(JSONPrintable printable, String mainColumn) {
        this.printable = printable;
        this.formatter = new ColumnsFormatter(mainColumn);
        this.loadColumns();
    }

    protected abstract void loadColumns();

    protected abstract TableRowContract makeItem(JSONObject json);

    public void draw(JSONArray array) throws JposException {
        WriterContract writer = printable.getWriter();

        this.printable.replicate('-');

        writer.writeBoldLine(this.formatter.getRow());

        this.printable.replicate('-');

        for (JSONObject json : array) {
            writer.writeBoldLine(this.makeItem(json).getRow());
        }

        this.printable.replicate('-');
    }

    protected void pushColumn(String title, int length, ColumnsFormatter.Alignment alignment) {
        this.formatter.push(title, length, alignment);
    }
}
