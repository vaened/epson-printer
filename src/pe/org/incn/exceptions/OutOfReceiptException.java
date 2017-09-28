package pe.org.incn.exceptions;

/**
 * OutOfReceiptException
 *
 * @author enea <enea.so@live.com>
 */
public class OutOfReceiptException extends BasePrinterException {

    public OutOfReceiptException() {
        super("Al parecer no hay papel");
    }

    public OutOfReceiptException(String message) {
        super(message);
    }
}
