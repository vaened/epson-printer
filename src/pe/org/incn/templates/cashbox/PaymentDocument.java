package pe.org.incn.templates.cashbox;

import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import jpos.JposException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import pe.org.incn.base.Command;
import pe.org.incn.base.EpsonPrintable;
import pe.org.incn.base.WriterContract;
import pe.org.incn.main.Configuration;
import pe.org.incn.support.Helpers;

/**
 * PaymentDocument
 *
 * @author enea <enea.so@live.com>
 */
abstract class PaymentDocument extends Document {

    /**
     * PaymentDocument constructor.
     *
     * @param json
     * @param printer
     */
    public PaymentDocument(JSONObject json, EpsonPrintable printer) {
        super(json, printer);
    }

    protected abstract void writeOwnerAttributes() throws JposException;

    protected abstract void totalsAttributes() throws JposException;

    @Override
    public void canvas() throws JposException {
        WriterContract writer = this.getWriter();

        /// print header
        this.writeHeader();

        /// Write title document
        writer.centerBoldWords(this.documentName().concat(" de venta electrónica").toUpperCase());

        writer.wrapper(
                w -> w.groupCenter("N°".concat(this.documentName().toUpperCase()), json("document")),
                w -> w.groupCenter("Moneda", json("money"))
        );

        this.breakLine();
        this.breakLine();

        /// Invoice data
        writer.groupWords("Cliente", json("client_name"));

        this.writeOwnerAttributes();

        writer.wrapper(
                /// Invoice date
                w -> w.groupOneLine("Emisión", json("issue_date")),
                w -> w.groupOneLine("Hora", json("issue_hour")),
                /// Client data
                w -> w.groupOneLine("H.C.", json("history")),
                w -> w.groupOneLine("D.N.I.", json("identity_document")),
                w -> w.groupOneLine("Prefactura", json("preinvoice")),
                w -> w.groupOneLine("Categoria", json("category"))
        );

        /// Display payment cotegory
//        writer.groupWords("Categoría de pago", json("category"));
        JSONArray elements = this.content();

        this.breakLine();
        this.breakLine();
        writer.centerBoldWords("DETALLE");

        this.separator();
        writer.writeBoldLine(Helpers.concat("Cant ", "Descripción"));
        this.separator();

        for (int i = 0, length = elements.length(); i < length; i++) {
            JSONObject obj = extractObject(elements, i);

            String description = value(obj, "facdes");
            if (description.length() > 37) {
                description = description.substring(0, 37);
            }
            writer.writeLine(Helpers.concat(" ", Helpers.rightAutocomplete(value(obj, "facnve"), 4), description.toLowerCase()));

            String price = moneyFormatter(value(obj, "facpri"));
            String total = moneyFormatter(value(obj, "totpar"));

            String spaces = Helpers.rightAutocomplete("", Configuration.getCanvasMaxWidth() - Helpers.concat(" precio: ", price, " total: ", total).length());

            String line = Helpers.concat(
                    spaces,
                    Command.prepare(" precio: ", Command.BOLD),
                    Command.prepare(price, Command.NORMAL),
                    Command.prepare(" total: ", Command.BOLD),
                    Command.prepare(total, Command.NORMAL)
            );

            writer.write(line, new String[]{});
            this.breakLine();
        }

        this.separator();

        String total = json("definitive_total");
        String subtotal = json("subtotal");

        writer.writeLine(
                Helpers.concat(
                        Command.prepare("Subtotal: ", Command.BOLD),
                        Command.prepare(moneyFormatter(subtotal), Command.NORMAL, Command.RIGHT)
                ),
                Command.BLANK_LINE
        );

        this.totalsAttributes();

        writer.writeLine(
                Helpers.concat(
                        Command.prepare("Total: ", Command.BOLD),
                        Command.prepare(moneyFormatter(total), Command.NORMAL, Command.RIGHT)
                ),
                Command.BLANK_LINE
        );

        writer.centerBoldWords(json("legend").toUpperCase());

        this.separator();

        writer.wrapper(
                w -> w.groupOneLine("CAJA", json("documentcashbox")),
                w -> w.groupOneLine("SECUENCIA", json("sequence")),
                w -> w.groupOneLine("CAJERO", json("username")),
                w -> w.groupOneLine("ORIGEN", json("origin"))
        );

        writer.groupOneLineWords("SERIE DE EQUIPO", json("equipment_series"));
        this.breakLine();
    }

    protected JSONObject extractObject(JSONArray elements, int index) {
        try {
            return elements.getJSONObject(index);
        } catch (JSONException ex) {
            Logger.getLogger(PaymentDocument.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    protected JSONArray content() {
        try {
            return this.json.getJSONArray("content");
        } catch (JSONException ex) {
            Logger.getLogger(PaymentDocument.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    protected String moneyFormatter(String money) {
        Double m = Double.parseDouble(money);
        return String.format(Locale.ROOT, "S/ %.2f", m);
    }
}
