package pe.org.incn.templates.helpers;

import jpos.JposException;
import pe.org.incn.base.JSONPrintable;
import pe.org.incn.base.WriterContract;
import pe.org.incn.support.Helpers;

/**
 * Header
 *
 * @author enea <enea.so@live.com>
 */
public class Header {

    private final JSONPrintable printable;

    public static Header make(JSONPrintable printable) {
        return new Header(printable);
    }

    public Header(JSONPrintable printable) {
        this.printable = printable;
    }

    public void printSimpleHeader() throws JposException {
        WriterContract writer = this.printable.getWriter();
        writer.centerBoldWords(this.printable.config("name").toUpperCase());
    }

    public void printHeader() throws JposException {
        WriterContract writer = this.printable.getWriter();
        writer.write("", new String[]{
            "\u001b|1B"
        });

        this.printable.breakLine();

        this.printSimpleHeader();
        writer.centerBoldWords(Helpers.concat("RUC ", this.printable.config("ruc")));

        this.printable.separator();
    }
}
