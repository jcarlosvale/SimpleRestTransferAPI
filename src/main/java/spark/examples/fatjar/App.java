package spark.examples.fatjar;

import org.jboss.weld.environment.se.events.ContainerInitialized;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

import static spark.Spark.get;
import static spark.Spark.path;

/**
 * Hello world!
 *
 */
public class App 
{
    @Inject
    private Hello hello;

    public void main( @Observes ContainerInitialized event )
    {
        path("api", () -> {
                    get("/inject", (req, res) -> {
                        return hello.message();
                    });
                });
        get("/hello", (req, res) -> "Hello World");
        get("/test", TestController.test);

    }
}
