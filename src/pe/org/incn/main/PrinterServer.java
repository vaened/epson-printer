package pe.org.incn.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import org.json.JSONException;
import org.json.JSONObject;
import pe.org.incn.base.BaseModule;
import pe.org.incn.base.EpsonPrintable;
import pe.org.incn.base.Printable;
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

    protected final int port = 9005;

    protected Socket connection;

    protected EpsonPrintable printer;

    public PrinterServer(EpsonPrintable printer) {
        this.printer = printer;
    }

    protected ServerSocket getServerConnection() throws IOException {
        return new ServerSocket(this.port);
    }

    public void open() throws IOException, JSONException {
        System.out.println("run");

        Dispatcher dispatcher = new Dispatcher(printer);

        Response response;

        serverSocket = this.getServerConnection();

        while (true) {
            System.out.println("init server");

            connection = serverSocket.accept();

            System.out.println("connect");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));

            String json;

            System.out.println("line");

            while ((json = in.readLine()) != null) {
                JSONObject obj = new JSONObject(json);

                try {
                    obj = this.extractBody(obj);

                    /// validate keys
                    this.validateRequiredKeys(obj);

                    /// Get requested module.
                    BaseModule module = dispatcher.makeModule(this.extractModuleName(obj));

                    /// Build template.
                    Printable printable = module.buildTemplate(this.extractTemplateName(obj), this.extractData(obj));

                    /// draw and print
                    printable.draw();

                    response = new Response("printed", "success", "200");

                } catch (NotFoundObject | NotFoundModuleException | NotFoundTemplateException e) {
                    response = new Response(e.getMessage(), "error", "404");
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

    protected String buildNotFoundKeyMessage(String key) {
        return String.format("The %s key was not found.", key);
    }

    /**
     * Extracts the body of the message and throws an exception in case of not
     * finding it.
     *
     * @param obj
     * @return
     * @throws JSONException
     */
    protected JSONObject extractBody(JSONObject obj) throws JSONException {
        if (!obj.has(Configuration.MAIN_CONTRAINER_KEY)) {
            throw new NotFoundObject(this.buildNotFoundKeyMessage(Configuration.MAIN_CONTRAINER_KEY));
        }

        return obj.getJSONObject(Configuration.MAIN_CONTRAINER_KEY);
    }

    /**
     * Extract the module name from json request
     *
     * @param json
     * @return
     * @throws JSONException
     */
    protected String extractModuleName(JSONObject json) throws JSONException {
        return json.getString(Configuration.MODULE_CONTAINER_KEY);
    }

    /**
     * Extract the template name from json request
     *
     * @param json
     * @return
     * @throws JSONException
     */
    protected String extractTemplateName(JSONObject json) throws JSONException {
        return json.getString(Configuration.TEMPLATE_CONTAINER_KEY);
    }

    /**
     * Extract the data from json request
     *
     * @param json
     * @return
     * @throws JSONException
     */
    protected JSONObject extractData(JSONObject json) throws JSONException {
        return json.getJSONObject(Configuration.DATA_CONTAINER_KEY);
    }

    /**
     * Validates that the required keys are present in the request and in case
     * of not finding them throws an exception.
     *
     * @param obj
     */
    protected void validateRequiredKeys(JSONObject obj) throws NotFoundObject {
        String[] keys = new String[]{
            Configuration.MODULE_CONTAINER_KEY,
            Configuration.TEMPLATE_CONTAINER_KEY,
            Configuration.DATA_CONTAINER_KEY
        };

        for (String key : keys) {
            if (!obj.has(key)) {
                throw new NotFoundObject(this.buildNotFoundKeyMessage(key));
            }
        }
    }
}
