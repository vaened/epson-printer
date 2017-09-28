package pe.org.incn.support;

import java.awt.TrayIcon.MessageType;
import java.util.Arrays;

/**
 * Response
 *
 * @author enea <enea.so@live.com>
 */
public class Response {

    private String code;
    private String status;
    private String response;

    public Response() {
    }

    public Response(String response, String status, String code) {
        this.response = response;
        this.status = status;
        this.code = code;
        Navbar.showNotification("Mensaje !!", response, this.getMessageStatus());
    }

    public Response(String response, String status, String code, Boolean noMessage) {
        this.response = response;
        this.status = status;
        this.code = code;
    }
    
    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the state
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the response
     */
    public String getResponse() {
        return response;
    }

    /**
     * @param response the response to set
     */
    public void setResponse(String response) {
        this.response = response;
    }

    public MessageType getMessageStatus() {
        if (this.isError()) {
            return MessageType.ERROR;
        }

        return MessageType.INFO;
    }

    public Boolean isError() {
        return Arrays.asList("404", "403", "500").contains(this.code);
    }

    public Boolean isSuccess() {
        return Arrays.asList("200").contains(this.code);
    }
}
