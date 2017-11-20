package pe.org.incn.templates.cashbox;

import jpos.JposException;
import pe.org.incn.base.EpsonPrintable;
import pe.org.incn.base.JSONPrintable;
import pe.org.incn.base.WriterContract;
import pe.org.incn.json.JSONArray;
import pe.org.incn.json.JSONObject;
import pe.org.incn.support.Helpers;
import pe.org.incn.templates.cashbox.useful.EspecialTableItem;
import pe.org.incn.templates.cashbox.useful.EspecialTableWrapper;
import pe.org.incn.templates.helpers.Header;

/**
 * EspecialConsolidatedTemplate
 *
 * @author enea <enea.so@live.com>
 */
public class EspecialConsolidatedTemplate extends JSONPrintable {

    public EspecialConsolidatedTemplate(JSONObject object, EpsonPrintable printer) {
        super(object, printer);
    }

    @Override
    protected void canvas() throws JposException {
        WriterContract writer = this.getWriter();

        Header.make(this).printSimpleHeader();
        this.jump();

        this.printHeader();

        this.jump(2);

        this.printTable();

        writer.wrapper(x -> x.groupJustified("Total Neto", object.json("total")));
    }

    private void printHeader() throws JposException {

        JSONObject header = this.object.getJSONObjec("header");

        writer.centerBoldWords("RESUMEN DE CAJA");
        writer.centerBoldWords(Helpers.concat("IMPRESIÓN:", " ", header.json("report_date")));

        this.jump();

        writer.groupOneLineWords("Cajero", header.json("cashier"));

        writer.wrapper(
                x -> x.groupOneLine("Inicio", header.json("initialized_at")),
                x -> x.groupOneLine("Fin", header.json("finalized_at"))
        );
    }

    private void printTable() throws JposException {
        JSONArray documents = this.getConsolidated();
        EspecialTableWrapper table = new EspecialTableWrapper(this);

        table.draw(documents);
    }

    private JSONArray getConsolidated() {
        return object.getJSONArray("consolidated");
    }

}
