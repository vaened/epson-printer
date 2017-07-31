package pe.org.incn.base;

import jpos.JposException;

/**
 * WriterContract
 *
 * @author enea <enea.so@live.com>
 */
public interface WriterContract {

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
}
