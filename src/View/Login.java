package View;

import DataBase.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.*;
import java.io.IOException;
import java.util.Objects;

public class Login
{
    @FXML private TextField textLogUserName;
    @FXML private PasswordField textLogPassword;
    @FXML private Button btnLogin;
    @FXML private Button btnNowSignup;
    @FXML private Button btnSignUp;
    @FXML private Button btnReturnLogin;
    @FXML private AnchorPane pane;
    @FXML private AnchorPane panelSignup;
    @FXML private TextField textSignName;
    @FXML private TextField textSignSurname;
    @FXML private TextField textSignUserName;
    @FXML private PasswordField textSignPassword;
    @FXML private Button btnLoginClose;
    @FXML private Button btnCloseSignup;

    static String nowUserName=null;
    static String nowNameAndSurname=null;


    public void signUpClick(ActionEvent event) {
        pane.setVisible(false);
        panelSignup.setVisible(true);
    }


    public void turnBackLogin(ActionEvent event) {
        panelSignup.setVisible(false);
        pane.setVisible(true);
    }


    public void closeScreen(MouseEvent event) { System.exit(0); }


    public void signUpClickDb(ActionEvent event)
    {
        UsersManager usersManager = new UsersManager();
        int control = usersManager.usernameControl(textSignUserName.getText());
        if(control==0 || textSignName.getText().equals("") || textSignSurname.getText().equals("") || textSignUserName.getText().equals("") || textSignPassword.getText().equals(""))
        {
            JOptionPane.showMessageDialog(null,"Incorrect or Incomplete Entry!!");
        }
        else if (control == 1)
        {
            usersManager.insert(textSignName.getText().toUpperCase(),textSignSurname.getText().toUpperCase(),textSignUserName.getText(),textSignPassword.getText());
            JOptionPane.showMessageDialog(null,"Subscribed");
            textSignName.setText("");
            textSignSurname.setText("");
            textSignUserName.setText("");
            textSignPassword.setText("");
        }
    }


    public void loginClick(ActionEvent event)
    {
        UsersManager usersManager = new UsersManager();
        int control = usersManager.loginControl(textLogUserName.getText(),textLogPassword.getText());
        if(control == 0)
        {
            try{
                nowUserName = textLogUserName.getText();
                nowNameAndSurname = usersManager.nameSurname(nowUserName);
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainScreen.fxml")));
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setScene(scene);
                stage.show();
            }catch (IOException exception) {
                System.out.println("Error000");
                System.out.println("Error : "+exception.getMessage());
                System.out.println(exception);
                exception.printStackTrace();
            }
            Stage stage = (Stage)btnLogin.getScene().getWindow();
            stage.close();
        } else {
            JOptionPane.showMessageDialog(null,"Incorrect Entry");
        }
    }


    @FXML
    public void btnEnteredLogin (MouseEvent event) { btnLogin.styleProperty().set("-fx-background-color: #3F51B5"); }
    @FXML
    public void btnExitedLogin (MouseEvent event) { btnLogin.styleProperty().set("-fx-background-color:  #283593"); }
    @FXML
    public void btnEnteredNowSignup (MouseEvent event) { btnNowSignup.styleProperty().set("-fx-background-color: #3F51B5"); }
    @FXML
    public void btnExitedNowSignup (MouseEvent event) { btnNowSignup.styleProperty().set("-fx-background-color:  #283593"); }
    @FXML
    public void btnEnteredSignup (MouseEvent event) { btnSignUp.styleProperty().set("-fx-background-color: #3F51B5"); }
    @FXML
    public void btnExitedSignup (MouseEvent event) { btnSignUp.styleProperty().set("-fx-background-color:  #283593"); }
    @FXML
    public void btnEnteredReturnLogin (MouseEvent event) { btnReturnLogin.styleProperty().set("-fx-background-color: #3F51B5"); }
    @FXML
    public void btnExitedReturnLogin (MouseEvent event) { btnReturnLogin.styleProperty().set("-fx-background-color:  #283593"); }
    @FXML
    public void btnEnteredCloseLogin (MouseEvent event) { btnLoginClose.styleProperty().set("-fx-background-color: #FFFFFF"); }
    @FXML
    public void btnExitedCloseLogin (MouseEvent event) { btnLoginClose.styleProperty().set("-fx-background-color:  #E3F2FD"); }
    @FXML
    public void btnEnteredCloseSignup (MouseEvent event) { btnCloseSignup.styleProperty().set("-fx-background-color: #FFFFFF"); }
    @FXML
    public void btnExitedCloseSignup (MouseEvent event) { btnCloseSignup.styleProperty().set("-fx-background-color:  #E3F2FD"); }
}