package pe.org.incn.base;

import pe.org.incn.json.JSONArray;
import pe.org.incn.json.JSONObject;

/**
 * JSONPrintable
 *
 * @author enea <enea.so@live.com>
 */
public abstract class JSONPrintable extends Printable {

    protected JSONObject object;

    public JSONPrintable(JSONObject object, EpsonPrintable printable) {
        super(printable);
        this.object = object;
    }

    public Boolean has(String key) {
        return this.has(this.object, key) ? json(key) != null : false;
    }

    public Boolean has(JSONObject object, String key) {
        return object.has(key) ? json(key) != null : false;
    }

    public String json(String key) {
        return this.object.json(key);
    }

    public Boolean is(String key) {
        return this.object.is(key);
    }

    public String json(String key, String defaultValue) {
        return this.object.json(key, defaultValue);
    }

    public JSONArray getJSONArray(String key) {
        return this.object.getJSONArray(key);
    }

    public String config(String key) {
        return this.getConfig().json(key);
    }

    public JSONObject getConfig() {
        return this.object.getJSONObjec("config");
    }
}
