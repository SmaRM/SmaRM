package org.smarthome.gwt.coreapp.server.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;

import javax.ws.rs.core.MediaType;

import com.ibm.icu.text.CharsetDetector;
import com.ibm.icu.text.CharsetMatch;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class HttpHelper {

    // HTTP timeout (10 sec. for debugging)
    private static final int HTTP_TIMEOUT_MS = 10000;

    public static String readFromUrl(String url) {
        String content = "";
        URLConnection urlConnection;
        try {
            urlConnection = new URL(url).openConnection();

            // Set connection timeout [ms]
            urlConnection.setConnectTimeout(HTTP_TIMEOUT_MS);

            // Open input stream for reading
            InputStream inputStream = urlConnection.getInputStream();

            CharsetDetector charsetDetector = new CharsetDetector();
            charsetDetector.setText(inputStream);
            CharsetMatch charsetMatch = charsetDetector.detect();

            if (charsetMatch == null) {
                String errMsg = String.format("Character set is not supported. (%s)", url);
                throw new IOException(errMsg);
            }

            Reader reader = charsetMatch.getReader();
            BufferedReader bufreader = new BufferedReader(reader);

            StringBuffer buf = new StringBuffer();
            String currentline = "";
            while ((currentline = bufreader.readLine()) != null) {
                buf.append(currentline);
            }
            content = buf.toString();
            bufreader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    public static String postFormRequest(String serviceUrl, MultivaluedMapImpl paramMap) {
        Client client = Client.create();
        WebResource webResource = client.resource(serviceUrl);

        Builder request = webResource.type(MediaType.APPLICATION_FORM_URLENCODED);

        ClientResponse response;
        if (paramMap != null)
            response = request.post(ClientResponse.class, paramMap);
        else
            response = request.post(ClientResponse.class);

        String responseText;
        if (response.getStatus() != 200) {
            responseText = "Failed. (HTTP error " + response.getStatus() + ")";
        } else {
            responseText = response.getEntity(String.class);
        }

        return responseText;
    }
    
    public static String callRestService(String serviceUrl, String... params) {
        String serviceCall = serviceUrl;
        for (String param : params) {
            serviceCall = serviceCall + "/" + param;
        }
        return readFromUrl(serviceCall);
    }

}
