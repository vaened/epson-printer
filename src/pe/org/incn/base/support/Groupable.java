package pe.org.incn.base.support;

import pe.org.incn.base.support.models.BaseWordsGroup;

/**
 * Groupable
 *
 * @author enea <enea.so@live.com>
 */
@FunctionalInterface
public interface Groupable {

    public BaseWordsGroup group(Writer group);
}
