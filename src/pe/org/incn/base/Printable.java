package pe.org.incn.base;

import java.util.logging.Level;
import java.util.logging.Logger;
import jp.co.epson.upos.UPOSConst;
import jpos.JposException;
import jpos.POSPrinterConst;
import pe.org.incn.main.Configuration;

/**
 *
 * @author enea <enea.so@live.com>
 */
public abstract class Printable {

    protected EpsonPrintable printer;

    protected WriterContract writer;

    /**
     * Printable constructor.
     *
     * @param printer
     */
    public Printable(EpsonPrintable printer) {
        this.printer = printer;
        this.setWriter(Configuration.mainWriter(printer));
    }
    
    /**
     * Establishes a writer for printing.
     *
     * @param writer
     */
    protected final void setWriter(WriterContract writer){
        this.writer = writer;
    }

    /**
     * Prepares the document to be printed and prints it.
     *
     * @return Printable
     */
    public Printable draw() {
        try {

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
                    printer.setBitmap(1, POSPrinterConst.PTR_S_RECEIPT, "C:\\logo.bmp",
                            (printer.getRecLineWidth() / 3), POSPrinterConst.PTR_BM_CENTER);

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

            this.printer.transactionPrint(POSPrinterConst.PTR_S_RECEIPT, POSPrinterConst.PTR_TP_TRANSACTION);

            this.canvas();

            this.printer.transactionPrint(POSPrinterConst.PTR_S_RECEIPT, POSPrinterConst.PTR_TP_NORMAL);
        } catch (JposException ex) {
            Logger.getLogger(Printable.class.getName()).log(Level.SEVERE, null, ex);
        }

        return this;
    }

    /**
     * Return to main writer.
     *
     * @return WriterContract
     */
    public WriterContract getWriter() {
        return this.writer;
    }

    /**
     * Draw a line break.
     *
     * @return Printable
     * @throws JposException
     */
    public Printable jump() throws JposException {
        return this.jump(1);
    }

    /**
     * Draw one or more line breaks.
     *
     * @param lines
     * @return Printable
     * @throws JposException
     */
    public Printable jump(int lines) throws JposException {
        lines *= 10;
        this.getWriter().writeLine(lines + Command.BREAK);
        return this;
    }

    /**
     * Draws the document to be printed.
     *
     * @throws jpos.JposException
     */
    protected abstract void canvas() throws JposException;
}
