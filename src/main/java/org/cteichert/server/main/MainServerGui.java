package org.cteichert.server.main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.cteichert.server.MainServer;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Configuration
@Component
public class MainServerGui extends Application {
    private BorderPane panelStatusCol;
    private TextArea textArea;
    private Button buttonStartServer;
    private Button buttonStopServer;

    private boolean stopReading = false;

    private ConfigurableApplicationContext context;

    @Override
    public void start(Stage primaryStage) {
        this.initializeServer(primaryStage);
    }

    private void initializeServer(Stage primaryStage) {
        primaryStage.setOnCloseRequest(event -> stopServer());

        primaryStage.setTitle("Matrchmaking Server");
        BorderPane mainPanel = new BorderPane();
        mainPanel.setPadding(new Insets(10, 10, 10, 10));

        HBox panelStatus = new HBox();
        BorderPane panelCenter = new BorderPane();
        Label label = new Label("Status:");
        panelStatusCol = new BorderPane();
        panelStatusCol.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
        panelStatusCol.setPrefSize(15, 15);
        panelStatus.getChildren().add(label);
        panelStatus.getChildren().add(panelStatusCol);
        HBox.setMargin(panelStatus, new Insets(4));
        HBox.setMargin(label, new Insets(4));
        HBox.setMargin(panelStatusCol, new Insets(4));

        textArea = new TextArea();
        textArea.setWrapText(true);
        textArea.setEditable(false);

        panelCenter.setCenter(textArea);

        buttonStartServer = new Button("Start");
        buttonStartServer.setOnAction(event -> startServer());

        buttonStopServer = new Button("Stop");
        buttonStopServer.setOnAction(event -> stopServer());
        buttonStopServer.setDisable(true);

        Button buttonClearText = new Button("Clear Log");
        buttonClearText.setOnAction(event -> textArea.clear());

        HBox panelButtons = new HBox();
        panelButtons.getChildren().add(buttonStartServer);
        panelButtons.getChildren().add(buttonStopServer);
        panelButtons.getChildren().add(buttonClearText);
        panelButtons.setPadding(new Insets(4, 4, 4, 4));
        panelButtons.setAlignment(Pos.CENTER_RIGHT);
        HBox.setMargin(buttonStartServer, new Insets(4));
        HBox.setMargin(buttonStopServer, new Insets(4));
        HBox.setMargin(buttonClearText, new Insets(4));

        mainPanel.setTop(panelStatus);
        mainPanel.setCenter(panelCenter);
        mainPanel.setBottom(panelButtons);

        Screen screen = Screen.getPrimary();
        int x = (int) ((screen.getVisualBounds().getWidth() - primaryStage.getWidth()) / 2);
        int y = (int) ((screen.getVisualBounds().getHeight() - primaryStage.getHeight()) / 2);
        primaryStage.setX(x);
        primaryStage.setY(y);
        primaryStage.setScene(new Scene(mainPanel));
        primaryStage.setMinHeight(320);
        primaryStage.setMinWidth(400);
        primaryStage.show();
    }

    public void startServer() {
        try {
            Thread thread = new Thread(() -> {
                stopReading = false;
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

                Thread innerThread = new Thread(() -> {
                    while (!stopReading) {
                        try {
                            String line;
                            while ((line = reader.readLine()) != null) {
                                String finalLine = line;
                                Platform.runLater(() -> updateText(finalLine));
                            }
                        } catch (Exception e) {
                        }
                    }
                });
                innerThread.setDaemon(false);
                innerThread.start();

                context = SpringApplication.run(MainServer.class, MainServer.args);

                updateText("Server information:");
                Environment env = context.getBean(Environment.class);
                int port = env.getProperty("server.port", Integer.class, 9696);
                updateText("Port: " + port);

                Platform.runLater(() -> {
                    panelStatusCol.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));

                    buttonStartServer.setDisable(true);
                    buttonStopServer.setDisable(false);

                    updateText("Server started...");
                });
            });

            thread.setDaemon(false);
            thread.start();
        } catch (Exception e) {
            e.printStackTrace();
            updateText("Server Start Esception: " + e.getLocalizedMessage());
        }
    }

    public void stopServer() {
        try {
            stopReading = true;
            context.close();
            panelStatusCol.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));

            buttonStartServer.setDisable(false);
            buttonStopServer.setDisable(true);

            updateText("Server stopped...");
        } catch (Exception e) {
            e.printStackTrace();
            updateText("Server Stop Esception: " + e.getLocalizedMessage());
        }
    }

    private void updateText(String text) {
        textArea.appendText("\n");
        textArea.appendText(text);
    }
}
