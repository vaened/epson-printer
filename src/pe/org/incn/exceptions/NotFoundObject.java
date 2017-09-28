package pe.org.incn.exceptions;

/**
 * NotFoundObject
 *
 * @author enea <enea.so@live.com>
 */
public class NotFoundObject extends BasePrinterException {

    public NotFoundObject() {
        super("The main key was not found");
    }

    public NotFoundObject(String message) {
        super(message);
    }
}
