/*Developed by Christian Zaccaria (20092351)
 * CA 1 - Rent-A-Home system
 */

package com.example.rentahome;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.regex.Pattern;

public class LoginController {

    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    @FXML
    private Label feedback;

    @FXML
    void onBackButtonClick(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();

        FXMLLoader fxmlLoader = new FXMLLoader(RentAHomeMain.class.getResource("rentAHome-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 480);

        stage.setTitle("Rent-A-Home");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void onLoginButtonClick(ActionEvent event) {
        String feedbackErrorMessage = "";
        if (email.getText().isBlank()) {
            feedbackErrorMessage += "Email field not completed \n";
        } else if (!isValidEmailAddress(email.getText())) {
            feedbackErrorMessage += "Not a valid Email address \n";

        }
        if (password.getText().isBlank()) {
            feedbackErrorMessage += "Password field not completed \n";
        }



            User foundUser = RentAHomeMain.users.get(email.getText());


            if (feedbackErrorMessage.isEmpty() && foundUser != null && password.getText().equals(foundUser.getUserPassword())
                    && foundUser.getUserType().equals("Administrator")) { //no errors
                try {
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                    FXMLLoader fxmlLoader = new FXMLLoader(RentAHomeMain.class.getResource("admin.fxml"));
                    Scene scene = new Scene(fxmlLoader.load(), 640, 480);

                    stage.setTitle("Administrator Page");
                    stage.setScene(scene);
                    stage.show();
                } catch (Exception e) {
                    feedback.setText("Unable to load main scene");
                }
            } else if (feedbackErrorMessage.isEmpty() && foundUser != null && password.getText().equals(foundUser.getUserPassword())
                    && foundUser.getUserType().equals("Agent") && foundUser.setUserEnabled()) { //no errors
                try {
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                    FXMLLoader fxmlLoader = new FXMLLoader(RentAHomeMain.class.getResource("agent.fxml"));
                    Scene scene = new Scene(fxmlLoader.load(), 640, 480);

                    stage.setTitle("Agent Page");
                    stage.setScene(scene);
                    stage.show();
                } catch (Exception e) {
                    feedback.setText("Unable to load main scene");
                }
            } else {
                feedback.setText(" Wrong email or password" + "\n" + feedbackErrorMessage);
            }
        }



    @FXML
    void onRegisterButtonClick(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();

        FXMLLoader fxmlLoader = new FXMLLoader(RentAHomeMain.class.getResource("register.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 480);

        stage.setTitle("Register Page");
        stage.setScene(scene);
        stage.show();
    }



    public boolean isValidEmailAddress(String email) {

        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();


    }

}