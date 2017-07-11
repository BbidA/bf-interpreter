package ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import rmi.RemoteHelper;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by liao on 2017/6/11.
 */
public class MainPane {

    private MenuBar menuBar;
    private Menu menuFile, menuUser, menuEdit, menuVersion, menuRun;
    private MenuItem itemNew, itemSave;
    private MenuItem itemRedo, itemUndo;
    private MenuItem itemLogout;
    private MenuItem itemRun;

    private TextArea inputArea, outputArea;
    private TreeView<String> treeView;

    private SplitPane splitPane, ioArea;
    private TabPane editTabPane;
    private BorderPane mainPane;

    private Scene mainScene;
    private Stage primaryStage;
    private Scene logOnScene;

    private RemoteHelper remoteHelper = RemoteHelper.getInstance();

    public MainPane(Stage primaryStage, Scene logOnScene) {

        this.logOnScene = logOnScene;
        this.primaryStage = primaryStage;
        mainPane = new BorderPane();
        mainScene = new Scene(mainPane, 1280, 720);
//        mainScene.getStylesheets().add(getClass().getResource("Theme.css").toExternalForm());

        createMenus();
        createEditor();
        createIOArea();

        addControlls();
    }

    private void createIOArea() {

        ioArea = new SplitPane();
        ioArea.setMaxHeight(150);

        inputArea = new TextArea();
        outputArea = new TextArea();
        outputArea.setEditable(false);

        ioArea.getItems().addAll(inputArea, outputArea);
        mainPane.setBottom(ioArea);
    }

    private void addControlls() {

        VBox barBox = new VBox();
        barBox.getChildren().addAll(menuBar);


        mainPane.setTop(barBox);
        mainPane.setCenter(splitPane);
    }

    private void createEditor() {

        editTabPane = new TabPane();
        splitPane = new SplitPane();

        TreeItem<String> root = new TreeItem<>("Files");
        treeView = new TreeView<>(root);
        treeView.setMaxWidth(150);
        treeView.setShowRoot(false);
        treeView.setEditable(false);

        try {
            for (Map.Entry<String, ArrayList<String>> entry : RemoteHelper.getInstance().getIOService().readFileMap(Main.username).entrySet()) {

                TreeItem<String> treeItem = new TreeItem<>(entry.getKey().split("_")[1]);
                root.getChildren().add(treeItem);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        treeView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        treeView.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {

            //版本控制部分
            Tab codeTab = new Tab(newValue.getValue());
            try {

                //点击一个新的文件时
                codeTab.setContent(new CodeEditArea(remoteHelper.getIOService().readFile(Main.username, newValue.getValue())));
                if (editTabPane.getTabs().isEmpty()) {
                    editTabPane.getTabs().add(codeTab);
                    editTabPane.getSelectionModel().select(codeTab);
                } else {
                    boolean canNotAdd = false;
                    for (Tab tab : editTabPane.getTabs()) {
                        if (tab.getText().equals(newValue.getValue())) {
                            canNotAdd = true;
                        }
                    }
                    if (!canNotAdd) {
                        editTabPane.getTabs().add(codeTab);
                    }
                }
//                menuVersion.getItems().clear();
//
//                //选择版本
//                for (String version : remoteHelper.getIOService().getVersions(Main.username, newValue.getValue())) {
//
//                    String[] tabNameSplit = version.split("_");
//                    String versionNum = tabNameSplit[2].split("\\.")[0];
//                    MenuItem menuItem = new MenuItem(versionNum);
//                    menuItem.setOnAction(ex -> {
//                        for (Tab tab : editTabPane.getTabs()) {
//                            if (tab.getText().equals(newValue.getValue())) {
//                                try {
//                                    String code = remoteHelper.getIOService().readFile(Main.username, newValue.getValue(), versionNum);
//                                    ((TextArea) tab.getContent()).setText(code);
//                                } catch (RemoteException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        }
//                    });
//                    menuVersion.getItems().add(menuItem);
//                }
                refreshVersion();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }));
        splitPane.getItems().addAll(treeView, editTabPane);
    }

    private void refreshVersion() {
        menuVersion.getItems().clear();

        //选择版本
        try {
            for (String version : remoteHelper.getIOService().getVersions(Main.username, treeView.getSelectionModel().getSelectedItem().getValue())) {

                String[] tabNameSplit = version.split("_");
                String versionNum = tabNameSplit[2].split("\\.")[0];
                MenuItem menuItem = new MenuItem(versionNum);
                menuItem.setOnAction(ex -> {
                    for (Tab tab : editTabPane.getTabs()) {
                        if (tab.getText().equals(treeView.getSelectionModel().getSelectedItem().getValue())) {
                            try {
                                String code = remoteHelper.getIOService().readFile(Main.username, treeView.getSelectionModel().getSelectedItem().getValue(), versionNum);
                                ((TextArea) tab.getContent()).setText(code);
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
                menuVersion.getItems().add(menuItem);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void createMenus() {

        menuBar = new MenuBar();

        menuFile = new Menu("File");
        menuUser = new Menu("User");
        menuEdit = new Menu("Edit");
        menuVersion = new Menu("Version");
        menuRun = new Menu("Run");

        itemNew = new MenuItem("New");
        itemSave = new MenuItem("Save");
        itemLogout = new MenuItem("Log out");
        itemRedo = new MenuItem("Redo");
        itemUndo = new MenuItem("Undo");
        itemRun = new MenuItem("Run");

        menuBar.getMenus().addAll(menuFile, menuEdit, menuUser, menuVersion, menuRun);
        menuFile.getItems().addAll(itemNew, itemSave);
        menuUser.getItems().addAll(itemLogout);
        menuEdit.getItems().addAll(itemRedo, itemUndo);
        menuRun.getItems().add(itemRun);

        //File
        itemNew.setOnAction(e -> {
            new NewFilePane();
        });

        itemSave.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
        itemSave.setOnAction(e -> {
            try {
                String content = ((TextArea) editTabPane.getSelectionModel().getSelectedItem().getContent()).getText();
                String filename = editTabPane.getSelectionModel().getSelectedItem().getText();
                RemoteHelper.getInstance().getIOService().writeFile(content, Main.username, filename);
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }
        });

        //User
        itemLogout.setOnAction(e -> {
            try {
                remoteHelper.getUserService().logout(Main.username);
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }
            Main.username = null;
            primaryStage.setScene(logOnScene);
        });

        //Edit
        itemRedo.setOnAction(e -> {
            ((CodeEditArea) editTabPane.getSelectionModel().getSelectedItem().getContent()).myRedo();
        });
        itemUndo.setOnAction(e -> {
            ((CodeEditArea) editTabPane.getSelectionModel().getSelectedItem().getContent()).myUndo();
        });

        //Run
        itemRun.setOnAction(event -> {
            System.out.println("touched");
            try {
                String exeResult = remoteHelper.getExecuteService().execute(((TextArea) editTabPane.getSelectionModel().getSelectedItem().getContent()).getText()
                        , inputArea.getText(), "." + editTabPane.getSelectionModel().getSelectedItem().getText().split("\\.")[1]);
                outputArea.setText(exeResult);
                System.out.println(exeResult);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }


    public Scene getMainScene() {
        return mainScene;
    }

    public class NewFilePane {

        private GridPane pane;
        private Stage stage;
        private TextField nameFiled;
        private Button sureButton, cancelButton;
        private ChoiceBox<String> choiceBox;

        NewFilePane() {
            pane = new GridPane();
            stage = new Stage();

            ObservableList<String> choiceBoxItems = FXCollections.observableArrayList(".ook", ".bf");
            choiceBox = new ChoiceBox<>(choiceBoxItems);
            choiceBox.getSelectionModel().select(1);

            nameFiled = new TextField();
            sureButton = new Button("确认");
            sureButton.setOnAction(e -> {

                if (!nameFiled.getText().isEmpty()) {

                    String fileName = nameFiled.getText() + choiceBox.getSelectionModel().getSelectedItem();
                    Tab codeTab = new Tab(fileName);
                    codeTab.setContent(new CodeEditArea(""));
                    editTabPane.getTabs().add(codeTab);

                    treeView.getRoot().getChildren().add(new TreeItem<>(fileName));
                    try {
                        System.out.println(nameFiled.getText() + choiceBox.getSelectionModel().getSelectedItem());
                        remoteHelper.getIOService().writeFile("", Main.username, fileName);
                    } catch (RemoteException e1) {
                        e1.printStackTrace();
                    }
                    stage.close();
                }
            });

            cancelButton = new Button("取消");
            cancelButton.setOnAction(e -> {
                stage.close();
            });

            HBox buttonBox = new HBox(15);
            buttonBox.getChildren().addAll(sureButton, cancelButton);
            buttonBox.setAlignment(Pos.BOTTOM_RIGHT);

            pane.add(nameFiled, 0, 0, 2, 1);
            pane.add(choiceBox, 0, 1, 2, 1);
            pane.add(buttonBox, 0, 2);
            pane.setHgap(15);
            pane.setVgap(15);
            pane.setPadding(new Insets(10));
            pane.setAlignment(Pos.CENTER);
            pane.setPrefSize(300, 150);

            Scene tmpScene = new Scene(pane);
            stage.setScene(tmpScene);

            stage.show();
            stage.setX(600);
            stage.setY(300);
            stage.setAlwaysOnTop(true);
        }
    }
}


