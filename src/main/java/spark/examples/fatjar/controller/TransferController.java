package spark.examples.fatjar.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.java.Log;
import spark.Request;
import spark.Response;
import spark.examples.fatjar.controller.exception.ContentTypeNotAcceptedException;
import spark.examples.fatjar.controller.exception.CustomException;
import spark.examples.fatjar.controller.exception.InternalServerErrorException;
import spark.examples.fatjar.dto.TransferDto;
import spark.examples.fatjar.service.TransferService;

import javax.inject.Inject;
import java.math.BigDecimal;

import static spark.Spark.*;

@Log
public class TransferController {

    private final TransferService transferService;
    private final Gson gson;

    @Inject
    public TransferController(TransferService transferService) {
        this.transferService = transferService;
        this.gson = new Gson();
    }

    public void createRoutes() {
        before(this::validateRequest);

        get("/ping", (req, res) -> "true");
        get("/json", this::generateJSON);
        get("/list", this::listAccounts);
        get("/persist", this::persist);

        path("/api", () -> {
            post("/transfer", this::transfer); //TODO Criar enum com paths?
            after((request, response) -> response.type("application/json"));
            exception(CustomException.class, this::handleCustomException);
        });

    }

    private String persist(Request request, Response response) {
        transferService.persist();
        return "OK";
    }

    private void handleCustomException(CustomException exception, Request request, Response response) {
        Gson gsonException = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        response.type("application/json");
        response.status(exception.getErrorCode());
        response.body(gsonException.toJson(exception));
    }

    private void validateRequest(Request request, Response response) {
        if (request.requestMethod().equalsIgnoreCase("POST") &&
                request.contentType() != "application/json") {
            log.warning("[ContentTypeNotAcceptedException] Request: " + request.body());
            throw new ContentTypeNotAcceptedException();
        }
    }

    private String transfer(final Request request, final Response response) {
        try {
            final TransferDto transferDto = gson.fromJson(request.body(),TransferDto.class);
            transferService.transfer(transferDto);
            return gson.toJson("Operation completed.");
        } catch (CustomException e) {
            log.warning("[CustomException] Request: " + request + " , cause: " + e);
            throw e;
        } catch (Exception e) {
            log.severe("[InternalServerErrorException] Request: " + request + ", cause: " + e);
            throw new InternalServerErrorException();
        }
    }

    private String generateJSON(Request request, Response response) {
        response.type("application/json");
        TransferDto transferDto = new TransferDto(1L, 2L, new BigDecimal(100.55));
        return gson.toJson(transferDto);
    }

    private String listAccounts(Request request, Response response) {
        response.type("application/json");

        return gson.toJson(transferService.getAccounts());
    }
}
