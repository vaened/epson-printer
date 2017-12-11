package pe.org.incn.templates.cashbox.useful;

import jpos.JposException;
import pe.org.incn.base.JSONPrintable;
import pe.org.incn.base.WriterContract;
import pe.org.incn.json.JSONArray;
import pe.org.incn.json.JSONObject;

/**
 * EspecialTableWrapper
 *
 * @author enea <enea.so@live.com>
 */
public class EspecialTableWrapper {

    private final JSONPrintable printable;
    private final ColumnsFormatter formatter;

    public EspecialTableWrapper(JSONPrintable printable) {
        this.printable = printable;
        this.formatter = new ColumnsFormatter("Descripción");
        this.loadColumns();
    }

    public void draw(JSONArray documents) throws JposException {
        WriterContract writer = printable.getWriter();

        this.printable.replicate('-');
        
        writer.writeBoldLine(this.formatter.getRow());
        
        this.printable.replicate('-');
        
        for (JSONObject document : documents) {
            EspecialTableItem row = new EspecialTableItem(document);
            writer.writeBoldLine(row.getRow());
        }

        this.printable.replicate('-');
    }

    private void loadColumns() {
        this.formatter.push("TRANS", Columns.AMOUNT.getLength(), ColumnsFormatter.Alignment.RIGTH);
        this.formatter.push("MONTO", Columns.TOTAL.getLength(), ColumnsFormatter.Alignment.RIGTH);
    }

    enum Columns {
        AMOUNT(7),
        TOTAL(13);

        private final int length;

        private Columns(int length) {
            this.length = length;
        }

        public int getLength() {
            return this.length;
        }
    }
}
