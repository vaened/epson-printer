package pe.org.incn.templates.cashbox;

import jpos.JposException;
import org.json.JSONObject;
import pe.org.incn.base.EpsonPrintable;

/**
 * TickeTemplate
 *
 * @author enea <enea.so@live.com>
 */
public class TickeTemplate extends PaymentDocument {

    public TickeTemplate(JSONObject json, EpsonPrintable printer) {
        super(json, printer);
    }

    @Override
    protected String documentName() {
        return "BOLETA";
    }

    @Override
    protected void writeOwnerAttributes() throws JposException {
    }

    @Override
    protected void totalsAttributes() throws JposException {
    }
}
