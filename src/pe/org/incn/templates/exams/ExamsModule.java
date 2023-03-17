package pe.org.incn.templates.exams;

import org.json.JSONObject;
import pe.org.incn.base.BaseModule;
import pe.org.incn.base.EpsonPrintable;
import pe.org.incn.base.Printable;
import pe.org.incn.exceptions.NotFoundTemplateException;

public class ExamsModule extends BaseModule {

    public ExamsModule(EpsonPrintable printer) {
        super(printer);
    }

    @Override
    public Printable buildTemplate(String template, JSONObject json) throws NotFoundTemplateException {
        if (template.equals("order")) {
            return new OrderTemplate(this.makeJSON(json), this.printer);
        }
        return null;
    }
}
