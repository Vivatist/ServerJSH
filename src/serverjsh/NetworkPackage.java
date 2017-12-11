package serverjsh;

import java.util.Date;

/**
 *
 * @author Andrey
 */
class NetworkPackage {
    
    private String clientRequest = null;
    private String serverResponse = null;
    final Date timeOfRequest = new Date();
    Date timeOfResponse = null;
    
    public NetworkPackage(String clientRequest) {
        if (clientRequest == null) {
            clientRequest = "Error: string is NULL";
        }
        this.clientRequest = clientRequest;
    }
    
    public Date getTimeOfRequest() {
        return timeOfRequest;
    }
    
    public Date getTimeOfResponse() {
        return timeOfResponse;
    }
    
    public void setServerResponse(String serverResponse) {
        this.serverResponse = serverResponse;
        this.timeOfResponse = new Date();
    }

    public String getClientRequest() {
        return clientRequest;
    }

    public String getServerResponse() {
        return serverResponse;
    }

}
