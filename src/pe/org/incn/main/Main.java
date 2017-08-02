/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pe.org.incn.main;

import java.util.logging.Level;
import java.util.logging.Logger;
import jpos.JposException;
import jpos.util.JposPropertiesConst;
import pe.org.incn.base.EpsonPrintable;
import pe.org.incn.base.Printer;
import pe.org.incn.templates.cashbox.PaymentDocument;


/**
 *
 * @author enea <enea.so@live.com>
 */
public class Server {

    public static void main(String[] args) {
        try {
            
            EpsonPrintable printer = new Printer();
            
            System.setProperty(JposPropertiesConst.JPOS_POPULATOR_FILE_PROP_NAME, "C:\\jpos.xml");
            
            //Open the device.
            //Use the name of the device that connected with your computer.
            printer.open("POSPrinter");

            //Get the exclusive control right for the opened device.
            //Then the device is disable from other application.
            printer.claim(1000);

            //Enable the device.
            printer.setDeviceEnabled(true);
            PaymentDocument s = new PaymentDocument(printer);
            s.draw();
            
            System.exit(0);
        } catch (JposException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
