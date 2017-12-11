package pe.org.incn.templates.cashbox.useful;

import pe.org.incn.json.JSONObject;

/**
 * CreditTableItem
 *
 * @author enea <enea.so@live.com>
 */
public class CreditTableItem implements TableRowContract {

    private final JSONObject item;

    public CreditTableItem(JSONObject item) {
        this.item = item;
    }

    @Override
    public String getRow() {
        ColumnsFormatter formatter = ColumnsFormatter.make(this.item.json("description"));
        formatter.push(this.item.json("total"), NoteCreditDetailWrapper.Columns.TOTAL.getLength(), ColumnsFormatter.Alignment.RIGTH);
        return formatter.getRow();
    }

}
