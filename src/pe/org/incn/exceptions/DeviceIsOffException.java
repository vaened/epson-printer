package pe.org.incn.exceptions;

/**
 * DeviceIsOffException
 *
 * @author enea <enea.so@live.com>
 */
public class DeviceIsOffException extends BasePrinterException {

    public DeviceIsOffException() {
        super("La fuente de alimentación del dispositivo está apagada");
    }

    public DeviceIsOffException(String message) {
        super(message);
    }
}
