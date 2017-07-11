package ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import rmi.RemoteHelper;
import runner.ClientRunner;

import java.rmi.RemoteException;

/**
 * Created by liao on 2017/6/3.
 */
public class Main extends Application {

    public Pane basePane;
    public static String username;
    private Scene scene;
    private LogOnPane logOnPane;

    @Override
    public void start(Stage primaryStage) throws Exception {

        logOnPane = new LogOnPane(primaryStage);

        basePane = new StackPane();
        basePane.getChildren().add(logOnPane.getLogPane());

        scene = new Scene(basePane, 400, 300);

        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> {
            try {
                if (username != null) {
                    RemoteHelper.getInstance().getUserService().logout(username);
                }
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }
        });
    }

    public static void main(String[] args) {
        new ClientRunner();
        launch(args);
    }
}
