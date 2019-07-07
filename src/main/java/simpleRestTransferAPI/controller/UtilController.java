package simpleRestTransferAPI.controller;

import com.google.gson.Gson;
import simpleRestTransferAPI.service.UtilService;

import javax.inject.Inject;

import static spark.Spark.get;
import static spark.Spark.path;

public class UtilController {

    private final UtilService utilService;
    private final Gson gson;

    @Inject
    public UtilController(UtilService utilService) {
        this.utilService = utilService;
        this.gson = new Gson();
    }

    public void createRoutes() {
        path("/api/private", () -> {
            get("/ping", (req, res) -> "true");
            get("/list", (req, res) -> listAccounts());
            get("/persist", (req, res) -> persist());
        });
    }

    private String persist() {
        utilService.persist();
        return "OK";
    }

    private String listAccounts() {
        return gson.toJson(utilService.getAccounts());
    }
}
