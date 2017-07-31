package pe.org.incn.base;

import java.util.logging.Level;
import java.util.logging.Logger;
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
        this.writer = Configuration.mainWriter(printer);
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
    public WriterContract getWriter()
    {
        return this.writer;
    }
    
    
    /**
     * Draw a line break.
     *
     * @return Printable
     * @throws JposException
     */
    public Printable jump() throws JposException
    {
        return this.jump(1);
    }
    
    /**
     * Draw one or more line breaks.
     *
     * @param lines
     * @return Printable
     * @throws JposException
     */
    public Printable jump(int lines) throws JposException
    {
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
