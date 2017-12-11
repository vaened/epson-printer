package pe.org.incn.templates.cashbox.useful;

import jpos.JposException;
import pe.org.incn.base.JSONPrintable;
import pe.org.incn.base.WriterContract;
import pe.org.incn.json.JSONArray;
import pe.org.incn.json.JSONObject;

/**
 * NoteCreditDetailWrapper
 *
 * @author enea <enea.so@live.com>
 */
public class NoteCreditDetailWrapper extends BaseWrapper {

    public NoteCreditDetailWrapper(JSONPrintable printable) {
        super(printable, "Descripción");
    }

    @Override
    protected void loadColumns() {
        this.pushColumn("MONTO", Columns.TOTAL.getLength(), ColumnsFormatter.Alignment.RIGTH);
    }

    @Override
    protected TableRowContract makeItem(JSONObject json) {
        return new CreditTableItem(json);
    }

    enum Columns {
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
