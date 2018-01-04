package pe.org.incn.json;

import java.util.AbstractList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;

/**
 * JSONArray
 *
 * @author enea <enea.so@live.com>
 */
public class JSONArray extends AbstractList<JSONObject> {

    private final org.json.JSONArray array;

    public JSONArray(org.json.JSONArray array) {
        this.array = array;
    }

    public JSONArray() {
        this.array = new org.json.JSONArray();
    }

    public JSONObject getJSONObject(int index) {
        try {
            return new JSONObject(this.array.getJSONObject(index));
        } catch (JSONException ex) {
            Logger.getLogger(JSONArray.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @Override
    public JSONObject get(int index) {
        return this.getJSONObject(index);
    }

    @Override
    public int size() {
        return this.array.length();
    }
}
