package pe.org.incn.support;

import java.util.HashMap;
import java.util.Map;
import pe.org.incn.base.BaseModule;
import pe.org.incn.base.EpsonPrintable;
import pe.org.incn.exceptions.NotFoundModuleException;
import pe.org.incn.templates.cashbox.CashboxModule;
import pe.org.incn.templates.exams.ExamsModule;

/**
 * Dispatcher
 *
 * @author enea <enea.so@live.com>
 */
public class Dispatcher {

    protected Map<String, BaseModule> classes;

    public Dispatcher(EpsonPrintable printer) {
        this.classes = new HashMap<>();
        this.classes.put("cashbox", new CashboxModule(printer));
        this.classes.put("exams", new ExamsModule(printer));
    }

    public Map<String, BaseModule> getClasses() {
        return this.classes;
    }

    public boolean isExistingModule(String key) {
        return this.getClasses().containsKey(key);
    }

    /**
     * Returns the module built by the key it represents.
     *
     * @param key
     * @return
     * @throws NotFoundModuleException
     */
    public BaseModule makeModule(String key) throws NotFoundModuleException {
        if (!this.isExistingModule(key)) {
            throw new NotFoundModuleException();
        }

        return this.getClasses().get(key);
    }
}
