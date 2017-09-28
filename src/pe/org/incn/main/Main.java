/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.org.incn.main;

import java.awt.SystemTray;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jpos.JposException;
import jpos.POSPrinterConst;
import jpos.util.JposPropertiesConst;
import org.json.JSONException;
import pe.org.incn.base.EpsonPrintable;
import pe.org.incn.support.Dispatcher;
import pe.org.incn.support.Navbar;
import pe.org.incn.base.Printer;

/**
 *
 * @author enea <enea.so@live.com>
 */
public class Main {

    public static void main(String[] args) {
        EpsonPrintable printer = new Printer();

        if (SystemTray.isSupported()) {
            new Navbar().loadMenu();
        }
        try {

            System.setProperty(JposPropertiesConst.JPOS_POPULATOR_FILE_PROP_NAME, "C:\\jpos.xml");

            //Open the device.
            //Use the name of the device that connected with your computer.
            printer.open("POSPrinter");

            //Get the exclusive control right for the opened device.
            //Then the device is disable from other application.
            printer.claim(1000);

            //Enable the device.
            printer.setDeviceEnabled(true);

            printer.setRecLetterQuality(true);
            printer.setRecLineChars(40);

            Configuration.setCanvasMaxWith(printer.getRecLineChars());
            Navbar.showInfoNotification("Mensaje", "Listo para imprimir");

        } catch (JposException ex) {
            Navbar.showErrorNotification("Error!!", ex.getMessage());
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        Dispatcher dispatcher = new Dispatcher(printer);

        PrinterServer connection = new PrinterServer(dispatcher);

        try {
            connection.open();
        } catch (IOException | JSONException | JposException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
