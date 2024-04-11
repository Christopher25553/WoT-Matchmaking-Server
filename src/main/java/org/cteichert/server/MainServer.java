package org.cteichert.server;

import javafx.application.Application;
import org.cteichert.server.main.MainServerGui;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MainServer {
    public static String[] args;

    public static void main(String[] args) {
        MainServer.args = args;
        Application.launch(MainServerGui.class, args);
    }
}
