package pe.org.incn.base;

import jpos.JposException;
import jpos.POSPrinterConst;

/**
 * SimpleWriter
 *
 * @author enea <enea.so@live.com>
 */
public class SimpleWriter implements WriterContract {

    protected EpsonPrintable printer;

    /**
     * SimpleWriter constructor.
     *
     * @param printer
     */
    public SimpleWriter(EpsonPrintable printer) {
        this.printer = printer;
    }

    /**
     * Write text in the document.
     *
     * @param text
     * @param commands
     * @return
     * @throws jpos.JposException
     */
    public WriterContract writer(String text, String... commands) throws JposException {

        String command = Command.prepare(commands);
        this.printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, command.concat(text));
        return this;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public WriterContract writeBoldLine(String text) throws JposException {
        return this.writer(text, new String[]{
            Command.BLANK_LINE,
            Command.BOLD
        });
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public WriterContract writeLine(String text) throws JposException {
        return this.writer(text, new String[]{
            Command.BLANK_LINE
        });
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public WriterContract centerWords(String text) throws JposException {
        this.centering(text, new String[]{
            Command.CENTER,
            Command.BLANK_LINE
        });

        return this;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public WriterContract centerBoldWords(String text) throws JposException {
        this.centering(text, new String[]{
            Command.CENTER,
            Command.BOLD,
            Command.BLANK_LINE
        });

        return this;
    }

    /**
     * Center words in the document.
     *
     * @param text
     * @param commands
     * @throws JposException
     */
    protected void centering(String text, String[] commands) throws JposException {
        int length = text.length(),
                max_width = this.getMaxWith();

        if (length <= max_width) {
            this.writer(text, commands);
            return;
        }

        int found = length;
        String prev = text;

        while (found > max_width) {
            found = prev.lastIndexOf(" ");
            prev = prev.substring(0, found);
        }

        this.writer(prev, commands);
        centering(text.substring(found, length), commands);
    }
    
    /**
     * Returns the maximum width of the canvas.
     *
     * @return
     * @throws JposException
     */
    protected int getMaxWith() throws JposException
    {
        return this.printer.getRecLineChars();
    }
}
