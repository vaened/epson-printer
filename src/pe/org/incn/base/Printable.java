package pe.org.incn.base;

import java.util.logging.Level;
import java.util.logging.Logger;
import jpos.JposException;
import jpos.POSPrinterConst;
import pe.org.incn.main.Configuration;
import pe.org.incn.support.Helpers;

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
     */
    protected void init() throws JposException {
    }

    /**
     * Prepares the document to be printed and prints it.
     *
     * @return Printable
     */
    public Printable draw() {
        try {

            this.init();

            this.printer.transactionPrint(POSPrinterConst.PTR_S_RECEIPT, POSPrinterConst.PTR_TP_TRANSACTION);

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
                writer.writeLine("", new String[]{Command.BLANK_LINE});
                writer.writeLine("", new String[]{Command.BLANK_LINE});
            }

            this.printer.transactionPrint(POSPrinterConst.PTR_S_RECEIPT, POSPrinterConst.PTR_TP_NORMAL);
        } catch (JposException ex) {
            Logger.getLogger(Printable.class.getName()).log(Level.SEVERE, null, ex);
        }

        return this;
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
     * @return
     * @throws JposException
     */
    public Printable replicate(char character) throws JposException {
        String line = "";

        for (int i = 0; i < Configuration.getCanvasMaxWidth(); i++) {
            line += character;
        }

        this.getWriter().write(line, new String[]{});
        return this;
    }

    /**
     * Draw a line break.
     *
     * @return Printable
     * @throws JposException
     */
    public Printable jump() throws JposException {
        return this.jump(2);
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
        lines *= 10;
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
