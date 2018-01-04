package pe.org.incn.templates.cashbox;

import java.util.logging.Level;
import java.util.logging.Logger;
import jp.co.epson.upos.UPOSConst;
import jpos.JposException;
import jpos.POSPrinterConst;
import pe.org.incn.base.EpsonPrintable;
import pe.org.incn.base.JSONPrintable;
import pe.org.incn.base.Printable;
import pe.org.incn.base.WriterContract;
import pe.org.incn.json.JSONArray;
import pe.org.incn.json.JSONObject;
import pe.org.incn.support.Helpers;

/**
 * Document
 *
 * @author enea <enea.so@live.com>
 */
public abstract class Document extends JSONPrintable {

    /**
     * Document constructor.
     *
     * @param json
     * @param printer
     */
    public Document(JSONObject json, EpsonPrintable printer) {
        super(json, printer);
    }

    /**
     * Returns the document name.
     *
     * @return
     */
    protected abstract String documentName();

    /**
     * Print the document header.
     *
     * @throws JposException
     */
    protected void writeHeader() throws JposException {
        WriterContract writer = this.getWriter();
        writer.write("", new String[]{
            "\u001b|1B"
        });

        this.breakLine();

        writer.centerBoldWords(config("name").toUpperCase());
        writer.centerBoldWords(config("address").toUpperCase());
        writer.centerBoldWords(Helpers.concat("RUC ", config("ruc")));

        /// Write a line separator
        this.separator();
    }

    /**
     *
     * @throws JposException
     */
    protected void writeFooter() throws JposException {
        writer.wrapper(
                w -> w.groupOneLine("CAJA", json("cashbox")),
                w -> w.groupOneLine("SECUENCIA", json("sequence")),
                w -> w.groupOneLine("CAJERO", json("cashier")),
                w -> w.groupOneLine("ORIGEN", json("origin"))
        );

        writer.groupOneLineWords("SERIE", json("equipment_series"));
    }

    protected void writeMessagesIfExists() throws JposException {
        JSONArray messages = this.object.getJSONArray("messages");

        if (!messages.isEmpty()) {
            this.replicate('-');
            this.breakLine();
            for (JSONObject message : messages) {
                writer.centerWords(message.get("message").toString());
            }
        }

        this.breakLine();
    }

    @Override
    protected void init() throws JposException {

        // JavaPOS's code for Step5
        //Even if using any printers, 0.01mm unit makes it possible to print neatly.
        printer.setMapMode(POSPrinterConst.PTR_MM_METRIC);
        // JavaPOS's code for Step5--END

        //Output by the high quality mode
        printer.setRecLetterQuality(true);

        boolean bSetBitmapSuccess = false;
        for (int iRetryCount = 0; iRetryCount < 5; iRetryCount++) {
            try {
                //Register a bitmap
                printer.setBitmap(1, POSPrinterConst.PTR_S_RECEIPT, "C:\\logo.bmp", (printer.getRecLineWidth() / 4), POSPrinterConst.PTR_BM_CENTER);

                bSetBitmapSuccess = true;
                break;
            } catch (JposException ex) {
                if (ex.getErrorCode() == UPOSConst.UPOS_E_FAILURE && ex.getErrorCodeExtended() == 0 && ex.getMessage().equals("It is not initialized.")) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex2) {
                        Logger.getLogger(Printable.class.getName()).log(Level.SEVERE, null, ex2);
                    }
                }
            }
        }
        if (!bSetBitmapSuccess) {
            System.err.println("error pe");
        }
    }
}
