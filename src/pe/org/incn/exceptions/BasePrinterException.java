package pe.org.incn.exceptions;

import pe.org.incn.support.Navbar;

/**
 * BasePrinterException
 *
 * @author enea <enea.so@live.com>
 */
public class BasePrinterException extends RuntimeException {

    public BasePrinterException(String message) {
        super(message);
        Navbar.showErrorNotification("Error!!", message);
    }
}
