package spark.examples.fatjar;

import com.google.gson.Gson;
import org.eclipse.jetty.client.HttpResponse;
import org.jboss.weld.environment.se.StartMain;
import org.jboss.weld.environment.se.events.ContainerInitialized;
import spark.examples.fatjar.domain.StatusResponse;
import spark.examples.fatjar.domain.TransferServiceResponse;
import spark.examples.fatjar.dto.TransferDto;
import spark.examples.fatjar.service.TransferService;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

import java.math.BigDecimal;

import static spark.Spark.get;
import static spark.Spark.post;

/**
 * Hello world!
 *
 */
public class App 
{
    @Inject
    private TransferService transferService;

    private Gson gson = new Gson();

    public static void main(String[] args) {
        new StartMain(args).go();
    }

    public void main( @Observes ContainerInitialized event )
    {

        get("/healthy", (request, response) -> "true");
        post("/transfer", (request, response) -> {
            response.type("application/json");
            //TODO: testar a deserializacao com null e valores invalidos;
            //TODO: aceitar somente JSON
            TransferDto transferDto = gson.fromJson(request.body(),TransferDto.class);
            transferService.transfer(transferDto);
            return gson.toJson((new TransferServiceResponse(StatusResponse.SUCCESS)));
        });
        get("/json", (request, response) -> {
            response.type("application/json");
            TransferDto transferDto = new TransferDto();
            transferDto.setOriginAccountId(1L);
            transferDto.setDestinyAccountId(2L);
            transferDto.setAmount(new BigDecimal(100.55));
            return gson.toJson(transferDto);
        });
    }
}
