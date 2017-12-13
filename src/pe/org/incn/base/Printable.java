package pe.org.incn.base;

import jpos.JposException;
import jpos.POSPrinterConst;
import pe.org.incn.exceptions.CanNotBeAccessed;
import pe.org.incn.exceptions.DeviceIsOffException;
import pe.org.incn.exceptions.DeviceWasDisconnected;
import pe.org.incn.exceptions.GlobalException;
import pe.org.incn.exceptions.OutOfReceiptException;
import pe.org.incn.main.Configuration;
import pe.org.incn.support.Helpers;
import pe.org.incn.support.Navbar;

/**
 *
 * @author enea <enea.so@live.com>
 */
public abstract class Printable {

    protected EpsonPrintable printer;

    protected WriterContract writer;

    /**
     * Printable constructor.
     *
     * @param printer
     */
    public Printable(EpsonPrintable printer) {
        this.printer = printer;
        this.setWriter(Configuration.mainWriter(printer));
    }

    /**
     * Establishes a writer for printing.
     *
     * @param writer
     */
    protected final void setWriter(WriterContract writer) {
        this.writer = writer;
    }

    /**
     * Init method.
     *
     * @throws jpos.JposException
     */
    protected void init() throws JposException {
        System.err.println("Imprimiendo....");
    }

    /**
     * Prepares the document to be printed and prints it.
     *
     * @return Printable
     */
    public Printable draw() {

        try {
            this.printer.setAsyncMode(false);

            this.getWriter().writeBoldLine("-");

            this.printer.setAsyncMode(true);

            this.init();

            this.canvas();

            if (Configuration.cuttable()) {
                this.printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, "\u001b|fP");
            } else {
                writer.writeLine("", new String[]{Command.BLANK_LINE});
                writer.writeLine("", new String[]{Command.BLANK_LINE});
                writer.writeLine("", new String[]{Command.BLANK_LINE});
                writer.writeLine("", new String[]{Command.BLANK_LINE});
                writer.writeLine("", new String[]{Command.BLANK_LINE});
                writer.writeLine("", new String[]{Command.BLANK_LINE});
                writer.writeLine("", new String[]{Command.BLANK_LINE});
            }
            
            Navbar.showInfoNotification("Listo", "Documento enviado");
        } catch (JposException exception) {
            System.err.println("------- START ERRORS -------");
            System.err.println(exception.getMessage());
            System.err.println(" ------- END ERRORS -------");
            Printable.verifyErrorException(exception);
        }

        return this;
    }

    /**
     * Verify if it is a custom exception.
     *
     * @param exception
     */
    public static void verifyErrorException(JposException exception) {
        switch (exception.getMessage()) {
            case "The power supply of the device is off.":
                throw new DeviceIsOffException();
            case "The device is offline.":
                throw new DeviceWasDisconnected();
            case "The exclusive access right had not been acquired.":
                throw new CanNotBeAccessed();
            case "Out of receipt form.":
                throw new OutOfReceiptException();
            default:
                throw new GlobalException(exception.getMessage());
        }
    }

    /**
     * Return to main writer.
     *
     * @return WriterContract
     */
    public WriterContract getWriter() {
        return this.writer;
    }

    public Printable separator() throws JposException {
        String line = "";

        for (int i = 0; i < Configuration.getCanvasMaxWidth(); i++) {
            line += "-";
        }

        this.getWriter().write(line, new String[]{Command.BLANK_LINE});
        return this;
    }

    /**
     * Replicates the character passed by parameter across the width of the
     * document.
     *
     *
     * @param character
     * @param comands
     * @return
     * @throws JposException
     */
    public Printable replicate(char character, String... comands) throws JposException {
        String line = "";

        for (int i = 0; i < Configuration.getCanvasMaxWidth(); i++) {
            line += character;
        }

        this.getWriter().write(line, comands);
        return this;
    }

    /**
     * Draw a line break.
     *
     * @return Printable
     * @throws JposException
     */
    public Printable jump() throws JposException {
        return this.jump(1);
    }

    /**
     * br html attribute.
     *
     * @return
     * @throws JposException
     */
    public Printable breakLine() throws JposException {
        this.getWriter().write("", new String[]{Command.BLANK_LINE});
        return this;
    }

    /**
     * Draw one or more line breaks.
     *
     * @param lines
     * @return Printable
     * @throws JposException
     */
    public Printable jump(Integer lines) throws JposException {
        lines *= 20;

        System.out.println(lines);
        String command = Helpers.concat(Command.HEX, lines.toString(), Command.BREAK);
        this.getWriter().write("", new String[]{command});
        return this;
    }

    /**
     * Draws the document to be printed.
     *
     * @throws jpos.JposException
     */
    protected abstract void canvas() throws JposException;
}
