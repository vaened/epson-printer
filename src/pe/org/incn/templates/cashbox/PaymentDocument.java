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
    public void canvas() throws JposException {
        this.getWriter()
                .centerWords("Instituto Nacional de Ciencias Neurológicas".toUpperCase())
                .writeLine("Documento de pago");
        
    }    
}
