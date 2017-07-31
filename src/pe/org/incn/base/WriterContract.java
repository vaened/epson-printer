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
     * @return
     * @throws JposException
     */
    public WriterContract writeBoldLine(String text) throws JposException;

    /**
     * Write bold text in the document.
     *
     * @param text
     * @return
     * @throws JposException
     */
    public WriterContract writeLine(String text) throws JposException;

    /**
     * Center bold text in the document
     *
     * @param text
     * @return
     * @throws JposException
     */
    public WriterContract centerBoldWords(String text) throws JposException;

    /**
     * Center text in the document
     *
     * @param text
     * @return
     * @throws JposException
     */
    public WriterContract centerWords(String text) throws JposException;
}
