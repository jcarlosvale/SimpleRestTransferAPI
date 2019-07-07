package simpleRestTransferAPI.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import lombok.extern.java.Log;
import simpleRestTransferAPI.dto.TransferDto;
import simpleRestTransferAPI.exception.ContentTypeNotAcceptedException;
import simpleRestTransferAPI.exception.CustomException;
import simpleRestTransferAPI.exception.InternalServerErrorException;
import simpleRestTransferAPI.exception.MalFormedJsonException;
import simpleRestTransferAPI.service.TransferService;
import spark.Request;
import spark.Response;

import javax.inject.Inject;

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

        path("/api", () -> {
            post("/transfer", (request, response) -> transfer(request));
            exception(CustomException.class, (exception, request, response) -> handleCustomException(exception, response));
        });

        after((request, response) -> response.type("application/json"));
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
            final TransferDto transferDto = gson.fromJson(request.body(), TransferDto.class);
            transferService.transfer(transferDto);
            return gson.toJson("Operation completed.");
        } catch (JsonSyntaxException e) {
            throw new MalFormedJsonException();
        } catch (CustomException e) {
            log.warning("[CustomException] Request: " + request + " , cause: " + e);
            throw e;
        } catch (Exception e) {
            log.severe("[InternalServerErrorException] Request: " + request + ", cause: " + e);
            throw new InternalServerErrorException();
        }
    }
}
