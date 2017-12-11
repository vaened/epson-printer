package pe.org.incn.templates.cashbox.useful;

import pe.org.incn.json.JSONObject;

/**
 * EspecialTableItem
 *
 * @author enea <enea.so@live.com>
 */
public class EspecialTableItem implements TableRowContract {

    private final JSONObject consolidated;

    private final ColumnsFormatter formatter;

    public EspecialTableItem(JSONObject consolidated) {
        this.consolidated = consolidated;
        this.formatter = ColumnsFormatter.make(this.getDescription());
        this.loadColumns();
    }

    public String getRow() {
        return this.formatter.getRow();
    }

    public String getDescription() {
        return consolidated.json("description").toLowerCase();
    }

    public String getTotal() {
        return consolidated.json("total");
    }

    public String getAmount() {
        return consolidated.json("amount");
    }

    public void loadColumns() {
        this.formatter.push(this.getAmount(), EspecialTableWrapper.Columns.AMOUNT.getLength(), ColumnsFormatter.Alignment.RIGTH);
        this.formatter.push(this.getTotal(), EspecialTableWrapper.Columns.TOTAL.getLength(), ColumnsFormatter.Alignment.RIGTH);
    }
}
