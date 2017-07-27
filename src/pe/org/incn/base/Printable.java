package pe.org.incn.base;

import java.util.logging.Level;
import java.util.logging.Logger;
import jpos.JposException;
import jpos.POSPrinterConst;


/**
 *
 * @author enea <enea.so@live.com>
 */
public abstract class Printable {

    protected EpsonPrintable printer;

    protected final String HEX = "\u001b|";
    
    /**
     * Printable constructor.
     *
     * @param printer
     */
    public Printable(EpsonPrintable printer) {
        this.printer = printer;
    } 
    
    /**
     * Prepares the document to be printed and prints it.
     *
     * @return Printable
     */
    public Printable draw()
    {
        try {
            this.printer.transactionPrint(POSPrinterConst.PTR_S_RECEIPT, POSPrinterConst.PTR_TP_TRANSACTION);

            this.drawing();
            
            this.printer.transactionPrint(POSPrinterConst.PTR_S_RECEIPT, POSPrinterConst.PTR_TP_NORMAL);
        } catch (JposException ex) {
            Logger.getLogger(Printable.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return this;
    }
   
    
    /**
     * Write a text in the document.
     *
     * @param text
     * @return EpsonPrintable
     * @throws JposException
     */
    protected EpsonPrintable write(String text) throws JposException
    {
        this.printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, HEX.concat(text));
        return this.printer;
    }

    /**
     * Write a text and jump line.
     *
     * @param text
     * @return
     * @throws JposException
     */
    protected EpsonPrintable writeLine(String text) throws JposException
    {
        return this.write(text.concat("\n"));
    }
    
    /**
     * Draw a line break.
     *
     * @return EpsonPrintable
     * @throws JposException
     */
    protected EpsonPrintable jump() throws JposException
    {
        return this.jump(1);
    }
    
    /**
     * Draw one or more line breaks.
     *
     * @param lines
     * @return EpsonPrintable
     * @throws JposException
     */
    protected EpsonPrintable jump(int lines) throws JposException
    {
        lines *= 10;
        return this.write(lines + Command.BREAK);
    }
        
    /**
     * Draws the document to be printed.
     *
     * @throws jpos.JposException
     */
    protected abstract void drawing() throws JposException;
    
}
