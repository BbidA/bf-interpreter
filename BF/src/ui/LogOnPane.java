package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import rmi.RemoteHelper;

import java.rmi.RemoteException;


/**
 * Created by liao on 2017/6/3.
 */
public class LogOnPane {

    private static final double TEXTFIELD_WIDTH = 300;
    private static final double TEXTFIELD_HEIGHT = 30;
    private static final double BUTTON_WIDTH = 50;
    private static final double BUTTON_HEIGHT = 30;

    private RemoteHelper remoteHelper;
    private BorderPane logPane;
    private GridPane signInPane;
    private GridPane signUpPane;
    private Button signInBtn, signUpBtn, sureBtn, cancelBtn;
    private Text titleText, messageText1, messageText2, testText;
    private TextField username, signUpName;
    private PasswordField password, signUpPassword;
    private Stage primaryStage;

    public LogOnPane(Stage primaryStage) {

        this.primaryStage = primaryStage;

        remoteHelper = RemoteHelper.getInstance();

        logPane = new BorderPane();
//        logPane.setStyle("-fx-background-color: #3c3f41;");

        messageText1 = new Text();
        messageText2 = new Text();
//        messageText1.setFill(Color.WHITE);
//        messageText2.setFill(Color.WHITE);
        testText = new Text();

        signUpPane = new GridPane();
        signUpPane.setVgap(15);
        signUpPane.setHgap(15);
        signUpPane.setPadding(new Insets(10));
        signUpPane.setAlignment(Pos.CENTER);
        createSignUpContent();
        addSignUpContent();

        signInPane = new GridPane();
        signInPane.setHgap(15);
        signInPane.setVgap(15);
        signInPane.setPadding(new Insets(10));
        signInPane.setAlignment(Pos.CENTER);
        createSignInContent();
        addSignInContent();

        logPane.setCenter(signInPane);
        logPane.setBottom(testText);
    }

    private void addSignUpContent() {

        signUpPane.add(signUpName, 1, 1);
        signUpPane.add(signUpPassword, 1, 2);

        HBox buttonBox = new HBox(15);
        buttonBox.getChildren().addAll(sureBtn, cancelBtn);
        buttonBox.setAlignment(Pos.BASELINE_RIGHT);

        signUpPane.add(buttonBox, 1, 4);
        signUpPane.add(messageText2, 1, 3);
    }

    private void createSignUpContent() {

        sureBtn = new Button("Sure");
        sureBtn.setStyle("-fx-background-color: #4A4F51; -fx-text-fill: white;");
        sureBtn.setMinSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        sureBtn.setOnMouseEntered(event -> {
            sureBtn.setScaleX(1.1);
            sureBtn.setScaleY(1.1);
        });
        sureBtn.setOnMouseExited(event -> {
            sureBtn.setScaleX(1);
            sureBtn.setScaleY(1);
        });
        sureBtn.setOnAction(e -> {
            try {
                if (signUpName.getText().isEmpty()) {
                    messageText2.setText("用户名不能为空");
                } else if (signUpPassword.getText().isEmpty()) {
                    messageText2.setText("密码不能为空");
                } else {
                    remoteHelper.getUserService().signUp(signUpName.getText(), signUpPassword.getText());
                    messageText2.setText("");
                    logPane.setCenter(signInPane);
                }
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }
        });

        cancelBtn = new Button("Cancel");
        cancelBtn.setStyle("-fx-background-color: #4A4F51; -fx-text-fill: white;");
        cancelBtn.setMinSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        cancelBtn.setOnMouseEntered(event -> {
            cancelBtn.setScaleX(1.1);
            cancelBtn.setScaleY(1.1);
        });
        cancelBtn.setOnMouseExited(event -> {
            cancelBtn.setScaleY(1);
            cancelBtn.setScaleX(1);
        });
        cancelBtn.setOnAction(e -> {
            messageText2.setText("");
            logPane.setCenter(signInPane);
        });


        signUpName = new TextField();
        signUpName.setPromptText("User Name");
        signUpName.setMinSize(TEXTFIELD_WIDTH, TEXTFIELD_HEIGHT);
        signUpName.setStyle("-fx-background-color: inherit; -fx-border-color: #646464; -fx-border-radius: 20;");

        signUpPassword = new PasswordField();
        signUpPassword.setPromptText("Password");
        signUpPassword.setMinSize(TEXTFIELD_WIDTH, TEXTFIELD_HEIGHT);
        signUpPassword.setStyle("-fx-background-color: inherit; -fx-border-color: #646464; -fx-border-radius: 20;");
    }

    private void addSignInContent() {

        signInPane.add(titleText, 0, 0, 2, 1);
        signInPane.add(username, 1, 1);
        signInPane.add(password, 1, 2);

        HBox buttonBox = new HBox(15);
        buttonBox.getChildren().addAll(signInBtn, signUpBtn);
        buttonBox.setAlignment(Pos.BOTTOM_RIGHT);
        signInPane.add(buttonBox, 1, 4);
        signInPane.add(messageText1, 1, 3);
    }

    private void createSignInContent() {

        //登陆按钮
        signInBtn = new Button("Sign In");
        signInBtn.setMinSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        signInBtn.setStyle("-fx-background-color: #4A4F51; -fx-text-fill: white;");
        signInBtn.setOnMouseEntered(event -> {
            signInBtn.setScaleX(1.1);
            signInBtn.setScaleY(1.1);
        });
        signInBtn.setOnMouseExited(event -> {
            signInBtn.setScaleY(1);
            signInBtn.setScaleX(1);
        });
        signInBtn.setOnAction(e -> {
            try {
                if (!remoteHelper.getUserService().login(username.getText(), password.getText())) {
                    messageText1.setText("用户名或密码错误");
                } else {
                    Main.username = username.getText();
                    MainPane mainPane = new MainPane(primaryStage, logPane.getScene());
                    primaryStage.setScene(mainPane.getMainScene());
                    primaryStage.setX(300);
                }
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }
        });

        //注册按钮
        signUpBtn = new Button("Sign Up");
        signUpBtn.setMinSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        signUpBtn.setStyle("-fx-background-color: #4A4F51; -fx-text-fill: white;");
        signUpBtn.setOnMouseEntered(event -> {
            signUpBtn.setScaleX(1.1);
            signUpBtn.setScaleY(1.1);
        });
        signUpBtn.setOnMouseExited(event ->{
            signUpBtn.setScaleY(1);
            signUpBtn.setScaleX(1);
        });
        signUpBtn.setOnAction(e -> {
            logPane.setCenter(signUpPane);
            messageText1.setText("");
        });

        //标题
        titleText = new Text("BF & OoK");
        titleText.setFont(Font.font("Dialog", 30));
//        titleText.setFill(Color.WHITE);

        //用户名输入
        username = new TextField();
        username.setPromptText("username");
        username.setMinSize(TEXTFIELD_WIDTH, TEXTFIELD_HEIGHT);
        username.setStyle("-fx-background-color: inherit; -fx-border-color: #646464; -fx-border-radius: 20;");

        //密码输入
        password = new PasswordField();
        password.setPromptText("password");
        password.setMinSize(TEXTFIELD_WIDTH, TEXTFIELD_HEIGHT);
        password.setStyle("-fx-background-color: inherit; -fx-border-color: #646464; -fx-border-radius: 20;");
    }

    public BorderPane getLogPane() {
        return logPane;
    }
}
