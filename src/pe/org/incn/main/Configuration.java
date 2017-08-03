package pe.org.incn.main;

import pe.org.incn.base.EpsonPrintable;
import pe.org.incn.base.SimpleWriter;
import pe.org.incn.base.WriterContract;

/**
 * @author enea <enea.so@live.com>
 */
public class Configuration {

    /**
     * Key containing the request body.
     */
    public static String MAIN_CONTRAINER_KEY = "body";

    /**
     * Key that contains the name of the module in the request.
     */
    public static String MODULE_CONTAINER_KEY = "module";

    /**
     * Key that contains the name of the template to use.
     */
    public static String TEMPLATE_CONTAINER_KEY = "template";

    /**
     * Key containing the data in the request.
     */
    public static String DATA_CONTAINER_KEY = "data";

    /**
     * Key containing the data in the request.
     */
    protected static Integer MAX_WIDTH;

    /**
     * Return the main writer.
     *
     * @param printer
     * @return WriterContract
     */
    public static WriterContract mainWriter(EpsonPrintable printer) {
        return new SimpleWriter(printer);
    }

    /**
     * Set the max width of canvas.
     * 
     * @param max_width
     */
    public static void setCanvasMaxWith(int max_width) {
        MAX_WIDTH = max_width;
    }

    /**
     * Get the max width of de canvas.
     *
     * @return
     */
    public static Integer getCanvasMaxWidth() {
        return MAX_WIDTH;
    }
}
