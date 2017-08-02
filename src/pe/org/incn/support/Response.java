package pe.org.incn.support;

/**
 * Response
 *
 * @author enea <enea.so@live.com>
 */
public class Response {

    private String code;
    private String state;
    private String response;

    public Response() {
    }

    public Response(String response, String status, String code) {
        this.response = response;
        this.state = status;
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
    public String getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
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

}
