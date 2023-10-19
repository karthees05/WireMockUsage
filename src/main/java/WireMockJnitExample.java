import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.*;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.assertEquals;

public class WireMockJnitExample {

    private WireMockServer wireMockServer;

    @Before
    public void setup() {
        // Start WireMock server on a specific port (e.g., 8082)
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
//    @ClassRule
//    public static WireMockClassRule wireMockRule = new WireMockClassRule(8082);
//
//    @Test
//    public void testGetRequest() throws Exception {
//        // Configure WireMock to mock a GET endpoint
//        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/get-example"))
//                .willReturn(WireMock.aResponse()
//                        .withStatus(200)
//                        .withBody("GET Request Mocked Response")));
//
//        HttpClient httpClient = HttpClients.createDefault();
//        HttpGet request = new HttpGet("http://localhost:8082/get-example");
//
//        HttpResponse response = httpClient.execute(request);
//
//        assertEquals(200, response.getStatusLine().getStatusCode());
//        String responseBody = EntityUtils.toString(response.getEntity());
//        assertEquals("GET Request Mocked Response", responseBody);
//    }

//
//
//    @Test
//    public void testPostRequest() throws Exception {
//        // Configure WireMock to mock a POST endpoint
//        WireMock.stubFor(WireMock.post(WireMock.urlEqualTo("/post-example"))
//                .willReturn(WireMock.aResponse()
//                        .withStatus(201)
//                        .withBody("POST Request Mocked Response")));
//
//        HttpClient httpClient = HttpClients.createDefault();
//        HttpPost request = new HttpPost("http://localhost:8082/post-example");
//        request.setEntity(new StringEntity("Request Data"));
//
//        HttpResponse response = httpClient.execute(request);
//
//        assertEquals(201, response.getStatusLine().getStatusCode());
//        String responseBody = EntityUtils.toString(response.getEntity());
//        assertEquals("POST Request Mocked Response", responseBody);
//    }
//
//    @Test
//    public void testPutRequest() throws Exception {
//        // Configure WireMock to mock a PUT endpoint
//        WireMock.stubFor(WireMock.put(WireMock.urlEqualTo("/put-example"))
//                .willReturn(WireMock.aResponse()
//                        .withStatus(204)
//                        .withBody("PUT Request Mocked Response")));
//
//        HttpClient httpClient = HttpClients.createDefault();
//        HttpPut request = new HttpPut("http://localhost:8082/put-example");
//
//        HttpResponse response = httpClient.execute(request);
//
//        assertEquals(204, response.getStatusLine().getStatusCode());
//        String responseBody = EntityUtils.toString(response.getEntity());
//        assertEquals("PUT Request Mocked Response", responseBody);
//    }
}
