package org.ferris.resiste.console.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Arrays;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;
import javax.inject.Inject;
import org.ferris.resiste.console.sql.SqlProperties;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@ApplicationScoped
public class Main {

    public static void main(String[] args) {
        SeContainer container
            = SeContainerInitializer.newInstance().initialize();

        Main main
            = container.select(Main.class).get();

        main.main(Arrays.asList(args));
    }

    @Inject
    protected SqlProperties sqlProperties;

    protected void main(List<String> args) {
        System.out.printf("%n");
        System.out.println("  ____           _ _       ");
        System.out.println(" |  _ \\ ___  ___(_) |_ ___ ");
        System.out.println(" | |_) / _ \\/ __| | __/ _ \\");
        System.out.println(" |  _ <  __/\\__ \\ | ||  __/");
        System.out.println(" |_| \\_\\___||___/_|\\__\\___|");
        System.out.printf("%n");

        System.out.print("Attempting to connect to database....");
        try {
            Connection conn = DriverManager.getConnection(
                sqlProperties.getUrl(),
                sqlProperties.getUsername(),
                sqlProperties.getPassword()
            );
            System.out.printf("[ OK ]%n");
        } catch (Exception e) {
            System.out.printf("[FAIL]%n");
            e.printStackTrace();
        }
        System.out.printf("EXIT%n");
    }

}
