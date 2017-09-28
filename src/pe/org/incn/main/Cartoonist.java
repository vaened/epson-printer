package pe.org.incn.main;

import jpos.JposException;
import org.json.JSONException;
import org.json.JSONObject;
import pe.org.incn.base.BaseModule;
import pe.org.incn.base.Printable;
import pe.org.incn.exceptions.NotFoundObject;
import pe.org.incn.support.Dispatcher;

/**
 * Cartoonist
 *
 * @author enea <enea.so@live.com>
 */
public class Cartoonist {

    protected JSONObject json;
    protected Dispatcher dispatcher;

    public Cartoonist(JSONObject json, Dispatcher dispatcher) {
        this.json = json;
        this.dispatcher = dispatcher;
    }

    public void run() throws JSONException, JposException {
        JSONObject obj = this.extractBody(this.json);

        /// validate keys
        this.validateRequiredKeys(obj);

        /// Get requested module.
        BaseModule module = dispatcher.makeModule(this.extractModuleName(obj));

        /// Build template.
        Printable printable = module.buildTemplate(this.extractTemplateName(obj), this.extractData(obj));

        /// draw and print
        printable.draw();
    }

    /**
     * Extracts the body of the message and throws an exception in case of not
     * finding it.
     *
     * @param obj
     * @return
     * @throws JSONException
     */
    protected JSONObject extractBody(JSONObject obj) throws JSONException {
        if (!obj.has(Configuration.MAIN_CONTRAINER_KEY)) {
            throw new NotFoundObject(this.buildNotFoundKeyMessage(Configuration.MAIN_CONTRAINER_KEY));
        }

        return obj.getJSONObject(Configuration.MAIN_CONTRAINER_KEY);
    }

    /**
     * Extract the module name from json request
     *
     * @param json
     * @return
     * @throws JSONException
     */
    protected String extractModuleName(JSONObject json) throws JSONException {
        return json.getString(Configuration.MODULE_CONTAINER_KEY);
    }

    /**
     * Extract the template name from json request
     *
     * @param json
     * @return
     * @throws JSONException
     */
    protected String extractTemplateName(JSONObject json) throws JSONException {
        return json.getString(Configuration.TEMPLATE_CONTAINER_KEY);
    }

    /**
     * Extract the data from json request
     *
     * @param json
     * @return
     * @throws JSONException
     */
    protected JSONObject extractData(JSONObject json) throws JSONException {
        return json.getJSONObject(Configuration.DATA_CONTAINER_KEY);
    }

    /**
     * Validates that the required keys are present in the request and in case
     * of not finding them throws an exception.
     *
     * @param obj
     */
    protected void validateRequiredKeys(JSONObject obj) throws NotFoundObject {
        String[] keys = new String[]{
            Configuration.MODULE_CONTAINER_KEY,
            Configuration.TEMPLATE_CONTAINER_KEY,
            Configuration.DATA_CONTAINER_KEY
        };

        for (String key : keys) {
            if (!obj.has(key)) {
                throw new NotFoundObject(this.buildNotFoundKeyMessage(key));
            }
        }
    }

    protected String buildNotFoundKeyMessage(String key) {
        return String.format("The %s key was not found.", key);
    }
}
