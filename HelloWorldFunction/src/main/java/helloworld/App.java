package helloworld;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Handler for requests to Lambda function.
 */
public class App implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent input, final Context context) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("X-Custom-Header", "application/json");

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent()
                .withHeaders(headers);
            //final String pageContents = this.getPageContents("https://checkip.amazonaws.com");

        String message = createMessage();
            return response
                    .withStatusCode(200)
                    .withBody(message);
    }

    private static String createMessage()  {
        try {
            return new JSONObject()
                    .put("message", "hello world")
                    .put("location", "Hello my World!")
                    .put("localtion", new JSONObject().put("key1", "value1"))
                    .toString();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private String getPageContents(String address) {

        URL url = null;
        try {
            url = new URL(address);
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            return br.lines().collect(Collectors.joining(System.lineSeparator()));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
