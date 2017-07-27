package pe.org.incn.main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import jpos.CashDrawer;
import jpos.CashDrawerControl113;
import jpos.POSPrinter;

/**
 * @author enea <enea.so@live.com>
 */
public class Configuration {

    /**
     * Defines the name of the ticker printer.
     */
    public static String printDefaultName = "Generic / Text Only";

    /**
     * Establishes the need to display the print dialog.
     */
    public static boolean printDialog = false;
    
    /**
     * Object containing the configuration.
     */
    protected Properties properties;

    /**
     * Path to configuration.
     */
    protected static final String PATH = "C:\\";
    
    /**
     * File to configuration.
     */
    protected static final String FILE = "settei.properties";
    
    /**
     * Constructor.
     * 
     * @param properties
     */
    public Configuration(Properties properties) {
        this.properties = properties;
    }

    public void load() {
        InputStreamReader in = null;

        try {
            in = new InputStreamReader(new FileInputStream(this.fileName()), "UTF-8");
            
            this.properties.load(in);
            
            POSPrinter  printer = new POSPrinter();
            CashDrawerControl113 drawer = (CashDrawerControl113) new CashDrawer();
            
            Configuration.printDialog = this.properties.getProperty(printDefaultName, "false").equals("true");            
            Configuration.printDefaultName = this.properties.getProperty("print_default_name", "Generic / Text Only");
            
        } catch (UnsupportedEncodingException | FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }

    }
    
    /**
     * Build the configuration file name.
     *
     * @return String
     */
    protected String fileName()
    {
        return PATH.concat("\\").concat(FILE);
    }

}
