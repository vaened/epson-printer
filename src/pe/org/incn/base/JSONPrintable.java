package pe.org.incn.base;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * JSONPrintable
 *
 * @author enea <enea.so@live.com>
 */
public abstract class JSONPrintable extends Printable {

    protected JSONObject json;

    public JSONPrintable(JSONObject json, EpsonPrintable printer) {
        super(printer);
        this.json = json;
    }

    protected String value(JSONObject json, String key, String defaultValue) {
        try {
            if (!json.has(key)) {
                return defaultValue;
            }

            Object o = json.get(key);

            if (o == null) {
                return defaultValue;
            }

            if (o instanceof Integer) {
                return o.toString();
            }

            if (o instanceof Double) {
                return o.toString();
            }

            return (String) o;
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    protected String value(JSONObject json, String key) {
        return this.value(json, key, "");
    }

    public String json(String key) {
        return this.json(key, "");
    }

    protected boolean hasConfiguration() {
        return this.json.has("config");
    }

    public String config(String key) {
        try {
            return this.value(json.getJSONObject("config"), key, "");
        } catch (JSONException e) {
            return "";
        }
    }

    public String config(String key, String defaultValue) {
        try {
            return this.value(json.getJSONObject("config"), key, defaultValue);
        } catch (JSONException e) {
            return defaultValue;
        }
    }

    /**
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public String json(String key, String defaultValue) {
        if (!this.json.has(key)) {
            return defaultValue;
        }

        return this.value(this.json, key, defaultValue);
    }

}
