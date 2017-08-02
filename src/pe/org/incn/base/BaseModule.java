package pe.org.incn.base;

import org.json.JSONException;
import org.json.JSONObject;
import pe.org.incn.exceptions.NotFoundTemplateException;

/**
 * BaseModule
 *
 * @author enea <enea.so@live.com>
 */
abstract public class BaseModule {

    protected final EpsonPrintable printer;

    /**
     * The module constructor must receive the data through a json object.
     *
     * @param printer
     */
    public BaseModule(EpsonPrintable printer) {
        this.printer = printer;
    }

    /**
     * Returns the name of the template to use for printing.
     *
     * @param json
     * @return
     */
    protected String extractTemplateName(JSONObject json) {
        try {
            return json.getString("template");
        } catch (JSONException ex) {
            return "";
        }
    }

    /**
     * It returns the template to print and in case of not finding it, throws an
     * exception.
     *
     * @param json
     * @param template
     * @return
     * @throws NotFoundTemplateException
     */
    public abstract Printable buildTemplate(String template, JSONObject json) throws NotFoundTemplateException;
}
