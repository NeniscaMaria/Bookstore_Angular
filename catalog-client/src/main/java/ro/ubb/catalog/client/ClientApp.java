package ro.ubb.catalog.client;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by radu.
 */
public class ClientApp {

    public static void main(String[] args) {
        System.out.println("Application starting...");

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(
                        "ro.ubb.catalog.client.config"
                );
        context.getBean(Console.class).runConsole();

        System.out.println("bye ");
    }
}
