package pe.org.incn.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import jpos.JposException;
import org.json.JSONException;
import org.json.JSONObject;
import pe.org.incn.exceptions.BasePrinterException;
import pe.org.incn.exceptions.DeviceIsOffException;
import pe.org.incn.exceptions.DeviceWasDisconnected;
import pe.org.incn.exceptions.GlobalException;
import pe.org.incn.exceptions.NotFoundModuleException;
import pe.org.incn.exceptions.NotFoundObject;
import pe.org.incn.exceptions.NotFoundTemplateException;
import pe.org.incn.support.Dispatcher;
import pe.org.incn.support.Response;

/**
 * PrinterServer
 *
 * @author enea <enea.so@live.com>
 */
public class PrinterServer {

    protected ServerSocket serverSocket;

    protected final int port = 9006;

    protected Socket connection;

    protected Dispatcher dispatcher;

    public PrinterServer(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    protected ServerSocket getServerConnection() throws IOException {
        return new ServerSocket(this.port);
    }

    public void open() throws IOException, JSONException, JposException {
        System.out.println("run");

        Response response;

        serverSocket = this.getServerConnection();

        while (true) {

            System.out.println("init server");

            connection = serverSocket.accept();

            System.out.println("connect");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));

            String json;

            System.out.println("line");

            while ((json = in.readLine()) != null) {
                JSONObject obj = new JSONObject(json);

                try {
                    Cartoonist cartoonist = new Cartoonist(obj, dispatcher);
                    cartoonist.run();

                    Boolean noDisplayMessage = true;
                    response = new Response("printed", "success", "200", noDisplayMessage);

                } catch (NotFoundObject | NotFoundModuleException | NotFoundTemplateException e) {
                    response = new Response(e.getMessage(), "error", "404");
                } catch (DeviceIsOffException | DeviceWasDisconnected | GlobalException e) {
                    response = new Response(e.getMessage(), "error", "500");
                } catch (BasePrinterException e) {
                    response = new Response(e.getMessage(), "error", "500");
                } catch (JposException | JSONException e) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
                    response = new Response(e.getMessage(), "error", "500");
                }

                JSONObject jsonResponse = new JSONObject(response);
                out.write(jsonResponse.toString() + "\n");
                out.flush();
            }

            out.close();
            in.close();
            connection.close();
        }
    }
}
