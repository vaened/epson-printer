package pe.org.incn.json;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;

/**
 * JSONObject
 *
 * @author enea <enea.so@live.com>
 */
public class JSONObject {

    private final org.json.JSONObject object;

    public JSONObject(org.json.JSONObject object) {
        this.object = object;
    }

    /**
     * @param key
     * @return a simple value
     */
    public String value(String key) {
        return this.value(key, "");
    }

    /**
     * @param key
     * @param defaultValue
     * @return a simple value
     */
    public String value(String key, String defaultValue) {
        Object value = this.get(key);

        if (value == null) {
            return defaultValue;
        }

        return value.toString();
    }

    /**
     * @param key
     * @return a value from json
     */
    public String json(String key) {
        return this.value(key);
    }

    /**
     * @param key
     * @param defaultValue
     * @return a value from json
     */
    public String json(String key, String defaultValue) {
        return this.value(key, defaultValue);
    }

    /**
     * Verify if the consiguration is loaded.
     *
     * @return
     */
    public Boolean hasConfig() {
        return this.has("config");
    }

    /**
     * @return the loaded configuration.
     */
    public JSONObject config() {
        try {
            return new JSONObject(this.object.getJSONObject("config"));
        } catch (JSONException ex) {
            Logger.getLogger(JSONObject.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    /**
     * @param key
     * @return object from json
     */
    public Object get(String key) {
        if (!this.has(key)) {
            return null;
        }

        try {
            return this.object.get(key);
        } catch (JSONException ex) {
            Logger.getLogger(JSONObject.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    /**
     * Verify if object exists on json
     *
     * @param key
     * @return
     */
    public Boolean has(String key) {
        return this.object.has(key);
    }

    /**
     * @param key
     * @return a json object
     */
    public JSONObject getJSONObjec(String key) {
        try {
            return new JSONObject(this.object.getJSONObject(key));
        } catch (JSONException ex) {
            Logger.getLogger(JSONObject.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    /**
     * @param key
     * @return a json array
     */
    public JSONArray getJSONArray(String key) {
        try {
            return new JSONArray(this.object.getJSONArray(key));
        } catch (JSONException ex) {
            Logger.getLogger(JSONObject.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
}
