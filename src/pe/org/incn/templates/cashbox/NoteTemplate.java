package pe.org.incn.templates.cashbox;

import jpos.JposException;
import pe.org.incn.base.EpsonPrintable;
import pe.org.incn.base.JSONPrintable;
import pe.org.incn.base.WriterContract;
import pe.org.incn.json.JSONArray;
import pe.org.incn.json.JSONObject;
import pe.org.incn.support.Helpers;
import pe.org.incn.templates.cashbox.useful.NoteCreditDetailWrapper;
import pe.org.incn.templates.helpers.Header;

/**
 * NoteTemplate
 *
 * @author enea <enea.so@live.com>
 */
public abstract class NoteTemplate extends Document {

    public NoteTemplate(JSONObject object, EpsonPrintable printable) {
        super(object, printable);
    }

    @Override
    protected void canvas() throws JposException {
        WriterContract writer = this.getWriter();

        this.writeHeader();
        this.jump();

        this.printDocumentHeader();

        this.printBody();

        writer.groupWords("Tipo", json("type"));
        writer.groupWords("Motivo", json("reason"));

        this.breakLine();
        this.writeFooter();
        this.writeMessagesIfExists();
    }

    private void printDocumentHeader() throws JposException {
        JSONObject header = this.object.getJSONObjec("header");

        writer.centerBoldWords(header.json("type").concat(" ELECTRÓNICA").toUpperCase());
        writer.wrapper(x -> x.groupCenter("N°DOCUMENTO", header.json("document")));

        this.breakLine();
    }

    private void printBody() throws JposException {
        JSONObject document = this.object.getJSONObjec("header").getJSONObjec("document_note");

        this.printOwner();

        writer.groupWords("Cliente", json("client_name"));

        writer.wrapper(
                w -> w.groupOneLine("H.C.", json("history")),
                w -> w.groupOneLine("Doc.Iden", json("identity_document")),
                /// Document date
                w -> w.groupOneLine("Emisión", json("emission_date")),
                w -> w.groupOneLine("Hora", json("emission_hour"))
        );

        this.breakLine();
        this.breakLine();

        writer.centerBoldWords("DOCUMENTO QUE MODIFICA");

        writer.wrapper(
                w -> w.groupMultiLine(document.json("type"), document.json("document")),
                w -> w.groupMultiLine("Emisión", document.json("date"))
        );

        
        this.printDetail();
        
        writer.wrapper(x -> x.groupJustified("Total", json("total")));
        this.replicate('-');
    }

    private void printDetail() throws JposException {
        NoteCreditDetailWrapper wrapper = new NoteCreditDetailWrapper(this);
        JSONArray detail = this.getDetail();
        wrapper.draw(detail);
    }

    private void printOwner() throws JposException {
        if (this.has("owner")) {
            JSONObject owner = this.object.getJSONObjec("owner");
            writer.groupWords("Razón social", owner.json("name").toUpperCase());
            writer.groupWords("R.U.C.", owner.json("ruc"));
        }
    }

    private JSONArray getDetail() {
        return object.getJSONArray("detail");
    }
}
