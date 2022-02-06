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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

public class RegisterController {

    @FXML
    private TextField name;

    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField confirmPassword;

    @FXML
    private TextField phoneNumber;

    @FXML
    private RadioButton agent;

    @FXML
    private RadioButton admin;

    @FXML
    private Label feedback;

    RentAHome rentAHome;



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
    void onLoginButtonClick(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();

        FXMLLoader fxmlLoader = new FXMLLoader(RentAHomeMain.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 480);

        stage.setTitle("Rent-A-Home");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void onRegisterButtonClick(ActionEvent event) {
        String feedbackErrorMessage ="";
        if(email.getText().isBlank()){
            feedbackErrorMessage+="Email field not completed \n";
        }
        else if(!isValidEmailAddress(email.getText())){
            feedbackErrorMessage+="Not a valid Email address \n";
        }
        if(password.getText().isBlank()){
            feedbackErrorMessage+="Password field not completed \n";
        }

        if(!password.getText().equals(confirmPassword.getText())){
            feedbackErrorMessage+="Your Password and confirm password are not the same \n";
        }

        if(name.getText().isBlank()){
            feedbackErrorMessage+="Name field not completed \n";
        }


        if(phoneNumber.getText().isBlank()){
            feedbackErrorMessage+="Phone Number field not completed \n";
        }

        else if(!onlyDigits(phoneNumber.getText(), phoneNumber.getText().length())){
            feedbackErrorMessage+="Phone number should only contain digit numbers!\n";
        }

        if(!agent.isSelected() && !admin.isSelected()){
            feedbackErrorMessage+="Agent or Administrator registration? \n";
        }

        if(agent.isSelected() && admin.isSelected()){
            feedbackErrorMessage+="Please choose only one option, Agent or Administrator \n";
        }

        //Parts of the code below is from https://www.programiz.com/java-programming/examples/get-key-from-hashmap-using-value
        for(Map.Entry<String, User> entry: RentAHomeMain.users.entrySet()) {

            if(Objects.equals(entry.getKey(), email.getText())) {
                feedbackErrorMessage+="Email already exists";
                break;
            }
        }

        if(feedbackErrorMessage.isEmpty() && (agent.isSelected() || admin.isSelected())) { //no errors
            String type;
            if(agent.isSelected()) {
                type = "Agent";

            }
            else{
                type="Administrator";

            }





            RentAHomeMain.users.put(email.getText(),new User(type,true,name.getText(),email.getText(),password.getText(),phoneNumber.getText()));
            try {
                RentAHomeMain.getRentAHome().saveUsers();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();

                FXMLLoader fxmlLoader = new FXMLLoader(RentAHomeMain.class.getResource("rentAHome-view.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 640, 480);

                stage.setTitle("Rent-A-Home");
                stage.setScene(scene);
                stage.show();
            }
            catch(Exception e){
                feedback.setText("Unable to load main scene");
            }
        }
        else{
            feedback.setText(feedbackErrorMessage);
        }
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

    //To check if phone number contains only digit numbers
    //from https://www.codegrepper.com/code-examples/java/how+to+check+if+a+string+contains+only+numbers
    public static boolean
    onlyDigits(String str, int n)
    {

        for (int i = 0; i < n; i++) {


            if (str.charAt(i) >= '0'
                    && str.charAt(i) <= '9') {
                return true;
            }
            else {
                return false;
            }
        }
        return false;
    }


}