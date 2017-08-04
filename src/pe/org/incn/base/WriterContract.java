package pe.org.incn.base;

import jpos.JposException;
import pe.org.incn.base.support.Groupable;

/**
 * WriterContract
 *
 * @author enea <enea.so@live.com>
 */
public interface WriterContract {

    /**
     * Write text in the document.
     *
     * @param text
     * @param commands
     * @param overrideCommands
     * @return
     * @throws jpos.JposException
     */
    public WriterContract write(String text, String[] commands, String... overrideCommands) throws JposException;

    /**
     * Write bold text in the document.
     *
     * @param text
     * @param commands
     * @return
     * @throws JposException
     */
    public WriterContract writeBoldLine(String text, String... commands) throws JposException;

    /**
     * Write bold text in the document.
     *
     * @param text
     * @param commands
     * @return
     * @throws JposException
     */
    public WriterContract writeLine(String text, String... commands) throws JposException;

    /**
     * Center bold text in the document
     *
     * @param text
     * @param commands
     * @return
     * @throws JposException
     */
    public WriterContract centerBoldWords(String text, String... commands) throws JposException;

    /**
     * Center text in the document
     *
     * @param text
     * @param commands
     * @return
     * @throws JposException
     */
    public WriterContract centerWords(String text, String... commands) throws JposException;

    /**
     * Write a block of text in a single line, separating the label from the
     * text with 2 points.
     *
     * @param label
     * @param text
     * @param commands
     * @return
     * @throws JposException
     */
    public WriterContract groupOneLineWords(String label, String text, String... commands) throws JposException;

    /**
     * Write a block of text on a separate line for the label and text.
     *
     * @param label
     * @param text
     * @param commands
     * @return
     * @throws JposException
     */
    public WriterContract groupWords(String label, String text, String... commands) throws JposException;

    /**
     * Contains multiple text groups.
     *
     * @param groupable
     * @return
     * @throws JposException
     */
    public WriterContract wrapper(Groupable... groupable) throws JposException;
}
