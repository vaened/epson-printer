package pe.org.incn.templates.cashbox;

import pe.org.incn.base.EpsonPrintable;
import pe.org.incn.json.JSONObject;

/**
 * CreditNoteTemplate
 *
 * @author enea <enea.so@live.com>
 */
public class CreditNoteTemplate extends NoteTemplate {

    public CreditNoteTemplate(JSONObject object, EpsonPrintable printable) {
        super(object, printable);
    }

    @Override
    protected String documentName() {
        return "NOTA DE CRÉDITO";
    }
}
