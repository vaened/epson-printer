package pe.org.incn.templates.cashbox;

import jpos.JposException;
import pe.org.incn.base.EpsonPrintable;
import pe.org.incn.base.Printable;

/**
 * PaymentDocument
 *
 * @author enea <enea.so@live.com>
 */
public class PaymentDocument  extends Printable{

    /**
     * PaymentDocument constructor.
     *
     * @param printer
     */
    public PaymentDocument(EpsonPrintable printer) {
        super(printer);
    }

    @Override
    protected void drawing() throws JposException {
        
        this.writeLine("text");
        this.jump();
        this.writeLine("text");
        this.writeLine("text");
        this.writeLine("text");
        this.jump(2);
        this.writeLine("text");
    }    
}
