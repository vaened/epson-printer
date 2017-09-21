package pe.org.incn.templates.cashbox;

import java.util.logging.Level;
import java.util.logging.Logger;
import jpos.JposException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import pe.org.incn.base.EpsonPrintable;
import pe.org.incn.base.WriterContract;
import pe.org.incn.base.support.GroupFormatter;
import pe.org.incn.main.Configuration;
import pe.org.incn.support.Helpers;

/**
 * PaymentDocument
 *
 * @author enea <enea.so@live.com>
 */
abstract class PaymentDocument extends Document {

    protected static final Integer QUANTITY_SPACES = 5;

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

        /// Invoice data
        this.writeOwnerAttributes();

        writer.groupWords("Cliente", json("client_name"));

        writer.wrapper(
                /// Invoice date
                w -> w.groupMultiLine("Emisión", json("issue_date")),
                w -> w.groupMultiLine("Hora", json("issue_hour")),
                /// Client data
                w -> w.groupMultiLine("H.C.", json("history")),
                w -> w.groupMultiLine("D.N.I.", json("identity_document")),
                w -> w.groupMultiLine("Prefactura", json("preinvoice")),
                w -> w.groupMultiLine("Categoria", json("category"))
        );

        /// Print detail
        this.printDetail();

        this.totalsAttributes();
        writer.writeLine(GroupFormatter.instance("Total", json("definitive_total")).makeSpaceBetween());

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

    protected void printDetail() throws JposException {
        JSONArray elements = this.content();

        this.breakLine();
        this.breakLine();
        writer.centerBoldWords("DETALLE");

        this.separator();
        writer.writeBoldLine(Helpers.concat("Can. ", "Descripción"));
        this.separator();

        for (int i = 0, length = elements.length(); i < length; i++) {
            JSONObject element = extractObject(elements, i);

            String line = Helpers.concat(this.buildQuantity(element), this.buildDescription(element));

            writer.writeLine(line);

            this.writeTotals(element);

            this.breakLine();
        }
    }

    protected void writeTotals(JSONObject element) throws JposException {

        String price = value(element, "base_price");
        String total = value(element, "total");

        GroupFormatter priceFormatter = GroupFormatter.instance("U.", price);
        GroupFormatter totalFormatter = GroupFormatter.instance("T.", total);

        int totalSize = priceFormatter.getTotalSize() + totalFormatter.getTotalSize() + 1;

        String spaces = Helpers.rightAutocomplete("", Configuration.getCanvasMaxWidth() - totalSize);

        String totals = Helpers.concat(
                spaces,
                priceFormatter.makeWithoutPadding(),
                " ",
                totalFormatter.makeWithoutPadding()
        );

        writer.write(Helpers.leftAutocomplete(totals, Configuration.getCanvasMaxWidth()), new String[]{});

        this.replicate('.');
    }

    protected String buildDescription(JSONObject element) {
        String description = value(element, "description");
        int limit = Configuration.getCanvasMaxWidth() - QUANTITY_SPACES;

        if (description.length() > limit) {
            description = description.substring(0, limit);
        }

        return Helpers.rightAutocomplete(description.toLowerCase(), limit);
    }

    protected String buildQuantity(JSONObject element) {
        String quantity = value(element, "quantity");
        return Helpers.rightAutocomplete(quantity, QUANTITY_SPACES);
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
}
