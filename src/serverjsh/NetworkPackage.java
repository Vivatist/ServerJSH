package serverjsh;

import java.util.Date;

/**
 *
 * @author Andrey
 */
class NetworkPackage {

        public NetworkPackage(String clientRequest) {
            this.clientRequest = clientRequest;
        }
        final Date timeOfRequest = new Date();
        Date timeOfResponse = null;

        public Date getTimeOfRequest() {
            return timeOfRequest;
        }

        public Date getTimeOfResponse() {
            return timeOfResponse;
        }
        private String clientRequest = null;
        private String serverResponse = null;

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
