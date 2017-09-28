package pe.org.incn.exceptions;

/**
 * NotFoundTemplateException
 *
 * @author enea <enea.so@live.com>
 */
public class NotFoundTemplateException extends BasePrinterException {

    public NotFoundTemplateException() {
        super("The template was not found");
    }

    public NotFoundTemplateException(String message) {
        super(message);
    }
}
