package pe.org.incn.base;

import jpos.JposException;
import jpos.POSPrinter;
import jpos.POSPrinterConst;

/**
 * Printer
 *
 * @author enea <enea.so@live.com>
 */
public class Printer extends POSPrinter implements EpsonPrintable {

    @Override
    public void beginTransactionPrint() throws JposException {
        this.transactionPrint(POSPrinterConst.PTR_S_RECEIPT, POSPrinterConst.PTR_TP_TRANSACTION);
    }

    @Override
    public void endTransactionPrint() throws JposException {
        this.transactionPrint(POSPrinterConst.PTR_S_RECEIPT, POSPrinterConst.PTR_TP_NORMAL);
    }
}
