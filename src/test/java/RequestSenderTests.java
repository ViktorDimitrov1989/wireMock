import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.github.tomakehurst.wiremock.stubbing.ServeEvent;
import dto.ResponseDto;
import feign.Feign;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;


public class RequestSenderTests {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(options()
            .port(8080)
            .maxRequestJournalEntries(100));

    @Test
    public void test(){
        stubFor(get(urlEqualTo("/test/getDataFromServer"))
        .willReturn(aResponse()
        .withHeader("Content-Type", "application/json")
                .withStatus(200)
                .withBody("{\"id\":456}")));

        RequestClient client = Feign.builder()
                .client(new OkHttpClient())
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .target(RequestClient.class, "http://localhost:8080");

        ResponseDto resp = client.sendGetRequest();
        ResponseDto resp1 = client.sendGetRequest();

        List<ServeEvent> events = getAllServeEvents();

        String debug = "";
    }


}
