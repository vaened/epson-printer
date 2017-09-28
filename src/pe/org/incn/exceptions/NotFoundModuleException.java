package pe.org.incn.exceptions;

/**
 * NotFoundModuleException
 *
 * @author enea <enea.so@live.com>
 */
public class NotFoundModuleException extends BasePrinterException {

    public NotFoundModuleException() {
        super("The module was not found");
    }

    public NotFoundModuleException(String message) {
        super(message);
    }
}
