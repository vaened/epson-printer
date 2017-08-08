package pe.org.incn.templates.cashbox;

import jpos.JposException;
import org.json.JSONObject;
import pe.org.incn.base.Command;
import pe.org.incn.base.EpsonPrintable;
import pe.org.incn.support.Helpers;

/**
 * InvoiceTemplate
 *
 * @author enea <enea.so@live.com>
 */
public class InvoiceTemplate extends PaymentDocument {

    public InvoiceTemplate(JSONObject json, EpsonPrintable printer) {
        super(json, printer);
    }

    @Override
    protected String documentName() {
        return "Factura";
    }

    @Override
    protected void writeOwnerAttributes() throws JposException {
        this.getWriter()
                .wrapper(
                        x -> x.groupMultiLine("Razón social", json("owner_name").toUpperCase()),
                        x -> x.groupMultiLine("RUC", json("owner_ruc").toUpperCase())
                );
    }

    @Override
    protected void totalsAttributes() throws JposException {
        String igv = json("definitive_igv");
        writer.writeLine(
                Helpers.concat(
                        Command.prepare("IGV: ", Command.BOLD),
                        Command.prepare(moneyFormatter(igv), Command.NORMAL, Command.RIGHT)
                ),
                Command.BLANK_LINE
        );
    }
}
