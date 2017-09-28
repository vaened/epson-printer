package pe.org.incn.exceptions;

/**
 * CanNotBeAccessed
 *
 * @author enea <enea.so@live.com>
 */
public class CanNotBeAccessed extends BasePrinterException {

    public CanNotBeAccessed() {
        super("No se pudo obtener acceso exclusivo a la impresora");
    }

    public CanNotBeAccessed(String message) {
        super(message);
    }
}
