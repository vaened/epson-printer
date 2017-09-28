package pe.org.incn.base;

import jpos.JposException;
import jpos.POSPrinterControl114;

/**
 *
 * @author efloresp
 */
public interface EpsonPrintable extends POSPrinterControl114 {

    /**
     * Start the print transanction.
     *
     * @throws JposException
     */
    public void beginTransactionPrint() throws JposException;

    /**
     * End the print transanction.
     *
     * @throws JposException
     */
    public void endTransactionPrint() throws JposException;
}
