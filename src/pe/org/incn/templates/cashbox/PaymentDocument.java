package pe.org.incn.templates.cashbox;

import jpos.JposException;
import pe.org.incn.base.Command;
import pe.org.incn.base.EpsonPrintable;
import pe.org.incn.base.WriterContract;
import pe.org.incn.base.support.GroupFormatter;
import pe.org.incn.json.JSONArray;
import pe.org.incn.json.JSONObject;
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
                w -> w.groupOneLine("Emisión", json("issue_date")),
                w -> w.groupOneLine("Hora", json("issue_hour")),
                /// Client data
                w -> w.groupOneLine("H.C.", json("history")),
                w -> w.groupOneLine("D.N.I.", json("identity_document")),
                w -> w.groupOneLine("Prefactura", json("preinvoice")),
                w -> w.groupOneLine("Categoria", json("category"))
        );

        this.printAditionalAttributes();

        /// Print detail
        this.printDetail();

        this.breakLine();

        writer.writeLine(GroupFormatter.instance("Subtotal", json("subtotal")).makeSpaceBetween());
        writer.writeLine(GroupFormatter.instance("Descuento", json("discount")).makeSpaceBetween());
        this.totalsAttributes();
        writer.writeLine(GroupFormatter.instance("Total", json("definitive_total")).makeSpaceBetween());

        this.breakLine();

        writer.centerBoldWords(json("legend").toUpperCase());

        this.separator();
        
        this.writeFooter();
    }

    /**
     * Extract and print the additional attributes.
     */
    protected void printAditionalAttributes() throws JposException {
        JSONArray elements = this.additional();

        if (elements == null || elements.isEmpty()) {
            this.breakLine();
            return;
        }

        for (int i = 0, length = elements.size(); i < length; i++) {
            JSONObject element = extractObject(elements, i);
            this.getWriter().groupOneLineWords(element.json("label"), element.json("text"));
        }
    }

    /// Draws the detail of the document
    protected void printDetail() throws JposException {
        JSONArray elements = this.content();

        this.breakLine();
        writer.centerBoldWords("DETALLE");

        this.separator();
        writer.writeBoldLine(Helpers.concat("Can. ", "Descripción"));
        this.separator();

        for (int i = 0, length = elements.size(); i < length; i++) {
            JSONObject element = extractObject(elements, i);

            String line = Helpers.concat(this.buildQuantity(element), this.buildDescription(element));

            writer.writeLine(line);

            this.writeTotals(element);

            this.breakLine();
        }
    }

    /// Extracts the detail totals and displays them.
    protected void writeTotals(JSONObject element) throws JposException {

        String price = element.json("base_price");
        String total = element.json("total");

        GroupFormatter priceFormatter = GroupFormatter.instance("p.u.", price);
        GroupFormatter totalFormatter = GroupFormatter.instance("tot.", total);

        int totalSize = priceFormatter.getTotalSize() + totalFormatter.getTotalSize() + 1;

        String spaces = Helpers.rightAutocomplete("", Configuration.getCanvasMaxWidth() - totalSize);

        String totals = Helpers.concat(
                spaces,
                priceFormatter.makeWithoutPadding(),
                " ",
                totalFormatter.makeWithoutPadding()
        );

        writer.write(Helpers.leftAutocomplete(totals, Configuration.getCanvasMaxWidth()), new String[]{});

        this.replicate('.', Command.BOLD);
    }

    protected String buildDescription(JSONObject element) {
        String description = element.json("description");
        int limit = Configuration.getCanvasMaxWidth() - QUANTITY_SPACES;

        if (description.length() > limit) {
            description = description.substring(0, limit);
        }

        return Helpers.rightAutocomplete(description.toLowerCase(), limit);
    }

    protected String buildQuantity(JSONObject element) {
        String quantity = element.json("quantity");
        return Helpers.rightAutocomplete(quantity, QUANTITY_SPACES);
    }

    protected JSONObject extractObject(JSONArray elements, int index) {
        return elements.getJSONObject(index);
    }

    protected JSONArray content() {
        return this.object.getJSONArray("content");
    }

    protected JSONArray additional() {
        return this.object.getJSONArray("additional");
    }
}
