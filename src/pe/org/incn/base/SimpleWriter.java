package pe.org.incn.base;

import java.util.ArrayList;
import java.util.List;
import jpos.JposException;
import jpos.POSPrinterConst;
import pe.org.incn.base.support.GroupLine;
import pe.org.incn.base.support.Groupable;
import pe.org.incn.base.support.Writer;
import pe.org.incn.base.support.models.BaseWordsGroup;
import pe.org.incn.main.Configuration;
import pe.org.incn.support.CollectionUtils;
import pe.org.incn.support.Helpers;

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
     * {@inheritDoc }
     */
    @Override
    public WriterContract write(String text, String[] commands, String... overrideCommands) throws JposException {
        text = Command.prepare(text, CollectionUtils.join(commands, overrideCommands));
        this.printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, text);
        return this;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public WriterContract writeBoldLine(String text, String... commands) throws JposException {
        return this.write(text, commands, new String[]{
            Command.BOLD,
            Command.BLANK_LINE
        });
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public WriterContract writeLine(String text, String... commands) throws JposException {
        return this.write(text, commands, new String[]{
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
     * {@inheritDoc }
     */
    @Override
    public WriterContract groupOneLineWords(String label, String text, String... commands) throws JposException {
        return commonGroup(label, text, true, commands);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public WriterContract groupWords(String label, String text, String... commands) throws JposException {
        return commonGroup(label, text, false, commands);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public WriterContract wrapper(Groupable... groupable) throws JposException {
        List<BaseWordsGroup> groups = new ArrayList<>();

        for (Groupable group : groupable) {
            groups.add(group.group(new Writer()));
        }

        for (BaseWordsGroup group : groups) {
            if (group.isValidLength()) {
                continue;
            }

            System.err.println(group.getPlainLabel() + "-" + group.getPlainText());
            this.groupWords(group.getPlainLabel(), group.getPlainText());
        }

        GroupLine groupLine = new GroupLine(groups.stream().filter(x -> x.isValidLength()));

        return this.write(groupLine.make(), new String[]{});
    }

    /**
     * Group text and label
     *
     * @param label
     * @param text
     * @param oneline
     * @param commands
     * @return
     * @throws JposException
     */
    protected WriterContract commonGroup(String label, String text, boolean oneline, String... commands) throws JposException {
        label += ":";

        this.write(label, oneline ? new String[]{
            Command.BOLD
        } : new String[]{
            Command.BOLD,
            Command.BLANK_LINE
        });

        text = " " + text;
        return this.writeLine(text, commands);
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

        if (Helpers.existInStringArray(commands, Command.LARGE_SIZE) || Helpers.existInStringArray(commands, Command.MEDIUM_SIZE)) {
            max_width = this.getMiddleWidth();
        }

        if (length <= max_width) {
            this.write(text, commands, overrideCommands);
            return;
        }

        int found = length;
        String prev = text;

        while (found > max_width) {
            found = prev.lastIndexOf(" ");
            prev = prev.substring(0, found);
        }

        this.write(prev, commands, overrideCommands);
        centering(text.substring(found, length), commands, overrideCommands);
    }

    /**
     * Returns the maximum width of the canvas.
     *
     * @return
     * @throws JposException
     */
    protected int getMaxWith() throws JposException {
        return Configuration.getCanvasMaxWidth();
    }

    /**
     * Returns the middle width of the canvas.
     *
     * @return
     * @throws JposException
     */
    protected int getMiddleWidth() throws JposException {
        return this.getMaxWith() / 2;
    }
}
