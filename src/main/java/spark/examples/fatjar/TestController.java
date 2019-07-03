package spark.examples.fatjar;

import spark.Request;
import spark.Response;
import spark.Route;


public class TestController {
    public static Route test = (Request req, Response response) -> "teste";
}
