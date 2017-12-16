package serverjsh.Network;

import java.util.Date;
import java.util.UUID;

/**
 *
 * @author Andrey
 */
public class NetworkPackage {
    
    public final UUID key = UUID.randomUUID();
    private String clientRequest = null;
    private String serverResponse = null;
    final Date timeOfRequest = new Date();
    Date timeOfLife = null;
    
    public NetworkPackage(String clientRequest) {
        if (clientRequest == null) {
            clientRequest = "Error: string is NULL";
        }
        this.clientRequest = clientRequest;
        this.timeOfLife = new Date();
    }
    
    public Date getTimeOfRequest() {
        return timeOfRequest;
    }
    
    public Date getTimeOfResponse() {
        return timeOfLife;
    }
    
    public void setServerResponse(String serverResponse) {
        this.serverResponse = serverResponse;
    }

    public String getClientRequest() {
        return clientRequest;
    }

    public String getServerResponse() {
        return serverResponse;
    }

}
