package pe.org.incn.support;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 * Navbar
 *
 * @author enea <enea.so@live.com>
 */
public class Navbar {

    public void loadMenu() {
        final PopupMenu popup = new PopupMenu();
        Image image = Toolkit.getDefaultToolkit().getImage("C:\\icon.gif");
        final TrayIcon trayIcon = new TrayIcon(image, "Listo para imprimir");
        final SystemTray tray = SystemTray.getSystemTray();

        MenuItem restart = new MenuItem("Reiniciar");
        MenuItem exit = new MenuItem("Cerrar");

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int config = JOptionPane.showConfirmDialog(null, "¿Seguro que desea cerrar el servidor?", "Cuidado",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.INFORMATION_MESSAGE);
                if (config == 0) {
                    System.exit(0);
                }
            }
        });

        popup.add(restart);
        popup.add(exit);
        popup.addSeparator();

        trayIcon.setPopupMenu(popup);
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.out.println("TrayIcon could not be added.");
        }
    }
}
