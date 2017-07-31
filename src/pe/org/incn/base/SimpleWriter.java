package pe.org.incn.base;

import jpos.JposException;
import jpos.POSPrinterConst;
import pe.org.incn.support.CollectionUtils;

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
     * @param overrideCommands
     * @return
     * @throws jpos.JposException
     */
    public WriterContract writer(String text, String[] commands, String[] overrideCommands) throws JposException {
        String command = Command.prepare(CollectionUtils.join(commands, overrideCommands));
        this.printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, command.concat(text));
        return this;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public WriterContract writeBoldLine(String text, String... commands) throws JposException {
        return this.writer(text, commands, new String[]{
            Command.BOLD,
            Command.BLANK_LINE
        });
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public WriterContract writeLine(String text, String... commands) throws JposException {
        return this.writer(text, commands, new String[]{
            Command.BLANK_LINE            
        });
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public WriterContract centerWords(String text, String... commands) throws JposException {
        this.centering(text, commands, new String[]{
            Command.CENTER,
            Command.BLANK_LINE            
        });

        return this;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public WriterContract centerBoldWords(String text, String... commands) throws JposException {
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
     * @param overrideCommands
     * @throws JposException
     */
    protected void centering(String text, String[] commands, String... overrideCommands) throws JposException {
        int length = text.length(),
                max_width = this.getMaxWith();

        if (length <= max_width) {
            this.writer(text, commands, overrideCommands);
            return;
        }

        int found = length;
        String prev = text;

        while (found > max_width) {
            found = prev.lastIndexOf(" ");
            prev = prev.substring(0, found);
        }

        this.writer(prev, commands, overrideCommands);
        centering(text.substring(found, length), commands, overrideCommands);
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
