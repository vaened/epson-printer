package pe.org.incn.exceptions;

/**
 * DeviceWasDisconnected
 *
 * @author enea <enea.so@live.com>
 */
public class DeviceWasDisconnected extends BasePrinterException {

    public DeviceWasDisconnected() {
        super("El dispositivo fue desconectado");
    }

    public DeviceWasDisconnected(String message) {
        super(message);
    }
}
