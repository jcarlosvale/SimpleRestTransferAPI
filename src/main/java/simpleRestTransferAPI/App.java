package simpleRestTransferAPI;

import org.jboss.weld.environment.se.StartMain;
import org.jboss.weld.environment.se.events.ContainerInitialized;
import simpleRestTransferAPI.controller.CorsFilter;
import simpleRestTransferAPI.controller.TransferController;
import simpleRestTransferAPI.controller.UtilController;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

import static spark.Spark.port;

public class App {

    @Inject
    private TransferController transferController;

    @Inject
    private UtilController utilController;

    public static void main(String[] args) {
        new StartMain(args).go();
    }

    public void configureRoutes( @Observes ContainerInitialized event ) {
        port(8080);
        CorsFilter.enableCORS();
        transferController.createRoutes();
        utilController.createRoutes();
    }
}
