package pe.org.incn.templates.cashbox;

import jpos.JposException;
import pe.org.incn.base.EpsonPrintable;
import pe.org.incn.json.JSONObject;

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
