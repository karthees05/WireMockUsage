import com.github.tomakehurst.wiremock.WireMockServer;
import org.apache.http.util.EntityUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.assertEquals;

public class WireMockJnitExample {

    private WireMockServer wireMockServer;

    @Before
    public void setup() {
        // Start WireMock server on a specific port (e.g., 8080)
        wireMockServer = new WireMockServer(8082);
        wireMockServer.start();

        // Configure WireMock to mock a specific API endpoint
        configureFor("localhost", 8082);
        stubFor(get(urlEqualTo("/example"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("Mocked Response")));
    }

    @After
    public void teardown() {
        // Stop WireMock server after the tests
        wireMockServer.stop();
    }

    @Test
    public void testMockedEndpoint() throws Exception {
        // Perform an HTTP request to the WireMock server
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet("http://localhost:8082/example");
        HttpResponse response = httpClient.execute(request);

        // Verify the response from the mock endpoint
        assertEquals(200, response.getStatusLine().getStatusCode());
        String responseBody = EntityUtils.toString(response.getEntity());
        assertEquals("Mocked Response", responseBody);
    }
}
