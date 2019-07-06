package spark.examples.fatjar.controller;

import com.despegar.http.client.GetMethod;
import com.despegar.http.client.HttpResponse;
import com.despegar.http.client.PostMethod;
import com.despegar.sparkjava.test.SparkServer;
import com.google.gson.Gson;
import org.junit.ClassRule;
import org.junit.Test;
import spark.examples.fatjar.dto.TransferDto;
import spark.examples.fatjar.service.TransferService;
import spark.servlet.SparkApplication;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class TransferControllerTest {

    public static class TestControllerTestSparkApplication implements SparkApplication {
        @Override
        public void init() {
            TransferController transferController = new TransferController(mock(TransferService.class));
            transferController.createRoutes();
        }
    }

    @ClassRule
    public static SparkServer<TestControllerTestSparkApplication> testServer =
            new SparkServer<>(TestControllerTestSparkApplication.class, 4567);

    @Test
    public void testPing() throws Exception {
        GetMethod get = testServer.get("/ping", false);
        HttpResponse httpResponse = testServer.execute(get);
        assertEquals(200, httpResponse.code());
        assertEquals("true", new String(httpResponse.body()));
    }

    @Test
    public void testContentTypeException() throws Exception {
        PostMethod post = testServer.post("/api/transfer", "{some value}", false);
        post.addHeader("Content-Type", "application/xml");
        HttpResponse httpResponse = testServer.execute(post);
        assertEquals(415, httpResponse.code());
    }

    @Test
    public void testTransfer() throws Exception {
        Gson gson = new Gson();
        TransferDto transferDto = new TransferDto(1L, 2L, new BigDecimal(100));
        PostMethod post = testServer.post("/api/transfer", gson.toJson(transferDto), false);
        post.addHeader("Content-Type", "application/json");
        HttpResponse httpResponse = testServer.execute(post);
        assertEquals(200, httpResponse.code());
    }

    @Test
    public void testEmptyBodyTransfer() throws Exception {
        PostMethod post = testServer.post("/api/transfer", "", false);
        post.addHeader("Content-Type", "application/json");
        HttpResponse httpResponse = testServer.execute(post);
        assertEquals(200, httpResponse.code());
    }

    @Test
    public void testMalFormedBodyTransfer() throws Exception {
        PostMethod post = testServer.post("/api/transfer", "{}", false);
        post.addHeader("Content-Type", "application/json");
        HttpResponse httpResponse = testServer.execute(post);
        assertEquals(200, httpResponse.code());
    }
}