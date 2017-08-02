package pe.org.incn.exceptions;

/**
 * NotFoundObject
 *
 * @author enea <enea.so@live.com>
 */
public class NotFoundObject extends RuntimeException {

    public NotFoundObject() {
    }

    public NotFoundObject(String message) {
        super(message);
    }
}
