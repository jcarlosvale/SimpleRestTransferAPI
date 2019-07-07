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
        before(((request, response) -> validateRequest(request)));

        path("/api/private", () -> {
            get("/ping", (req, res) -> "true");
            get("/list", (req, res) -> listAccounts());
            get("/persist", (req, res) -> persist());
        });

        path("/api", () -> {
            post("/transfer", (request, response) -> transfer(request));
            exception(CustomException.class, (exception, request, response) -> handleCustomException(exception, response));
        });

        after((request, response) -> response.type("application/json"));
    }

    private String persist() {
        transferService.persist();
        return "OK";
    }

    private void handleCustomException(CustomException exception, Response response) {
        Gson gsonException = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        response.type("application/json");
        response.status(exception.getErrorCode());
        response.body(gsonException.toJson(exception));
    }

    private void validateRequest(Request request) {
        if (request.requestMethod().equalsIgnoreCase("POST") &&
                request.contentType() != "application/json") {
            log.warning("[ContentTypeNotAcceptedException] Request: " + request.body());
            throw new ContentTypeNotAcceptedException();
        }
    }

    private String transfer(final Request request) {
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

    private String generateJSON(Response response) {
        response.type("application/json");
        TransferDto transferDto = new TransferDto(1L, 2L, new BigDecimal(100.55));
        return gson.toJson(transferDto);
    }

    private String listAccounts() {
        return gson.toJson(transferService.getAccounts());
    }
}
