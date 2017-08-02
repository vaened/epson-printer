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
import jpos.util.JposPropertiesConst;
import org.json.JSONException;
import pe.org.incn.base.EpsonPrintable;
import pe.org.incn.base.Printer;
import pe.org.incn.support.Navbar;


/**
 *
 * @author enea <enea.so@live.com>
 */
public class Main {
   
    
    public static void main(String[] args) {
        EpsonPrintable printer = new Printer();
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
            
            
            
        } catch (JposException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (SystemTray.isSupported()) {
            new Navbar().loadMenu();
        }
        
        PrinterServer connection = new PrinterServer(printer);
        
        try {
            connection.open();
        } catch (IOException | JSONException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
