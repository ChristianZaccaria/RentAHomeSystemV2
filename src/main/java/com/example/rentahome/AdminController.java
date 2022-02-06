/*Developed by Christian Zaccaria (20092351)
* CA 1 - Rent-A-Home system
*/

package com.example.rentahome;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class AdminController implements Initializable {

    @FXML
    private TextField id;


    @FXML
    private TextField name;

    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    @FXML
    private TextField phoneNumber;



    @FXML
    private TextField userType;

    @FXML
    private TextField enable;

    @FXML
    private Label feedbackMain;

    @FXML
    private TextField propertyId;

    @FXML
    private ComboBox county;

    @FXML
    private ComboBox town;

    @FXML
    private ComboBox category;

    @FXML
    private ComboBox price;

    @FXML
    private ListView listAgent;

    @FXML
    private TextArea propertyTextArea;

    @FXML
    private Label feedbackBottom;

    RentAHome rentAHome;

   /* @FXML
    void onClearAgentButtonClick(ActionEvent event) {
        listAgent.getItems().clear();
        feedbackMain.setText("List of Users have been cleared!");
    }*/



    @FXML
    void onClearDetailsButtonClick(ActionEvent event) {
        name.clear();
        email.clear();
        password.clear();
        phoneNumber.clear();
        userType.clear();
        enable.clear();
        feedbackMain.setText("Details have been cleared!");


    }

    @FXML
    void onClearPropertyButtonClick(ActionEvent event) {
        listAgent.getItems().clear();
    }

    @FXML
    void onCreateButtonClick(ActionEvent event) {
        String feedbackErrorMessage = "";
        if (name.getText().isBlank()) {
            feedbackErrorMessage += "Name field not completed \n";
        } else if (!isValidEmailAddress(email.getText())) {
            feedbackErrorMessage += "Not a valid Email address \n";
        }
        if (password.getText().isBlank()) {
            feedbackErrorMessage += "Password field not completed \n";
        }
        if (phoneNumber.getText().isBlank()) {
            feedbackErrorMessage += "Phone Number field not completed \n";
        }
        else if(!onlyDigits(phoneNumber.getText(), phoneNumber.getText().length())){
            feedbackErrorMessage+="Phone number should only contain digit numbers!\n";
        }

        if (userType.getText().isBlank()) {
            feedbackErrorMessage += "Agent or Administrator? \n";
        }

        if (!userType.getText().equals("Agent") && !userType.getText().equals("Administrator")){
            feedbackErrorMessage += "Please type exactly 'Agent' or 'Administrator' in user type field \n";
        }




        if (enable.getText().isBlank()) {
            feedbackErrorMessage += "User is enabled? True or False \n";
        }

        if (!enable.getText().equals("True") && !enable.getText().equals("False")){
            feedbackErrorMessage += "Please type exactly 'True' or 'False' in Enabled field \n";
        }

        //Parts of the code below is from https://www.programiz.com/java-programming/examples/get-key-from-hashmap-using-value
        for(Map.Entry<String, User> entry: RentAHomeMain.users.entrySet()) {

            if(Objects.equals(entry.getKey(), email.getText())) {
                feedbackErrorMessage+="Email already exists";
                break;
            }
        }

        if (feedbackErrorMessage.isEmpty()) { //no errors


            RentAHomeMain.users.put(email.getText(), new User(userType.getText(), true, name.getText(), email.getText(), password.getText(),
                    phoneNumber.getText()));
            feedbackMain.setText("User created Successfully");
            try {
                rentAHome.saveUsers();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            feedbackMain.setText(feedbackErrorMessage);
        }
    }

    @FXML
    void onDeleteAgentButtonClick(ActionEvent event) {


        try {
            String emailKey = ((User) listAgent.getSelectionModel().getSelectedItem()).getUserEmail();
            if (emailKey != null) {

                Alert alert =
                        new Alert(Alert.AlertType.WARNING,
                                "Delete the User?",
                                ButtonType.OK,
                                ButtonType.CANCEL);
                alert.setTitle("Delete User Warning");
                Optional<ButtonType> result = alert.showAndWait(); //Notice showAndWait method
/*
=================End of Alert Code Display =======================
         Now Check to see if Delete confirmed
 */
                if (result.get() == ButtonType.OK) {

                    feedbackMain.setText("User Deleted: " + ((User) listAgent.getSelectionModel().getSelectedItem()).getUserName());
                    rentAHome.deleteAgent(emailKey);
                    try {
                        rentAHome.saveUsers();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    feedbackMain.setText("Delete Cancelled");
                }


                //The below code displays All Agents
                listAgent.getItems().clear();
                for (Map.Entry<String, User> entry : RentAHomeMain.users.entrySet()) {
                    listAgent.getItems().add(entry.getValue());
                }
            } else {
                feedbackMain.setText("Please select a User");
            }
        }
        catch (NullPointerException e){
            feedbackMain.setText("Please select a User");
        }
        }


    @FXML
    void onDeletePropertyButtonClick(ActionEvent event) {
        try {
            int id1 = Integer.parseInt(propertyId.getText());
            Property property = rentAHome.getPropertyDetailsById(id1);  //Returns link to the Product object (or null if it does not exist)

            if (property != null) {


                Alert alert =
                        new Alert(Alert.AlertType.WARNING,
                                "Delete this Property?",
                                ButtonType.OK,
                                ButtonType.CANCEL);
                alert.setTitle("Delete Property Warning");
                Optional<ButtonType> result = alert.showAndWait(); //Notice showAndWait method
/*
=================End of Alert Code Display =======================
         Now Check to see if Delete confirmed
 */
                if (result.get() == ButtonType.OK) {
                    if (rentAHome.deleteProductByID(id1)) {  //
                        feedbackBottom.setText("Property Deleted");
                        try {
                            rentAHome.saveProperties();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    feedbackBottom.setText("Delete Cancelled");
                }  //End of Alert check
            } //end of valid product id


            else { //deal with invalid product id entered -- null was returned
                feedbackBottom.setText("Invalid Property id");
            }
        }
        //if the id was not a number then we display the error.
        catch(NumberFormatException e){
            feedbackBottom.setText("Invalid Property id");

        }
    }

    @FXML
    void onDisplayAgentButtonClick(ActionEvent event) {

        listAgent.getItems().clear();
        for(Map.Entry<String, User> entry : RentAHomeMain.users.entrySet()){
            listAgent.getItems().add(entry.getValue());
            feedbackMain.setText("List of users has been displayed!");

        }
        //agentTextArea.setText(rentAHome.listAllAgents());
    }

    @FXML
    void onDisplayPropertyButtonClick(ActionEvent event) {
        propertyTextArea.setText(rentAHome.listAllProperties());
        feedbackBottom.setText("List of properties has been displayed!");
    }

    @FXML
    void onLogoutButtonClick(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();

        FXMLLoader fxmlLoader = new FXMLLoader(RentAHomeMain.class.getResource("rentAHome-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 480);

        stage.setTitle("Rent-A-Home");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void onReadButtonClick(ActionEvent event) {

        try {
            String emailKey = ((User) listAgent.getSelectionModel().getSelectedItem()).getUserEmail();
            if (emailKey != null) {
                name.setText(((User) listAgent.getSelectionModel().getSelectedItem()).getUserName());
                email.setText(((User) listAgent.getSelectionModel().getSelectedItem()).getUserEmail());
                password.setText(((User) listAgent.getSelectionModel().getSelectedItem()).getUserPassword());
                phoneNumber.setText(((User) listAgent.getSelectionModel().getSelectedItem()).getUserPhone());
                userType.setText(((User) listAgent.getSelectionModel().getSelectedItem()).getUserType());
                enable.setText("True");
            }
            else{
                feedbackMain.setText("Please select a User");
            }
        }
        catch (NullPointerException e){
            feedbackMain.setText("Please select a User");
        }
    }


    @FXML
    void onReadPropertyButtonClick(ActionEvent event) {
        int id1;

        try{
            id1 =Integer.parseInt(propertyId.getText());
        }
        catch(Exception e){
            feedbackBottom.setText("Error in id field");
            return;
        }
        Property p = rentAHome.getPropertyDetailsById(id1); //Returns link to the Product object (or null if it does not exist)
        if (p != null) { //check that there is a product object with the id entered
            propertyTextArea.setText(p.toString());

            county.setValue(p.getCounty());
            town.setValue(p.getTown());
            category.setValue(p.getCategory());
            price.setValue(p.getPrice());
            feedbackBottom.setText("valid product id");
        } else {

            feedbackBottom.setText("Invalid Product id");
        }
    }

    @FXML
    void onSearchButtonClick(ActionEvent event) {
        if (county.getValue().equals("County") ||
                town.getValue().equals("Town") ||
                category.getValue().equals("Category") ||
                price.getValue().equals("Price"))
        {
            feedbackBottom.setText("Please select county, town, category, and price");
        }
        else {
            propertyTextArea.setText(rentAHome.listAllPropertiesSpecific((String) county.getValue(),
                    (String) town.getValue(), (String) category.getValue(), (String) price.getValue()));
        }
    }

    @FXML
    void onUpdateButtonClick(ActionEvent event) {
        String feedbackErrorMessage = "";
        if (name.getText().isBlank()) {
            feedbackErrorMessage += "Name field not completed \n";
        } else if (!isValidEmailAddress(email.getText())) { //can use other email checking e.g. Apache
            feedbackErrorMessage += "Not a valid Email address \n";
        }
        if (password.getText().isBlank()) {
            feedbackErrorMessage += "Password field not completed \n";
        }
        if (phoneNumber.getText().isBlank()) {
            feedbackErrorMessage += "Phone Number field not completed \n";
        }
        else if(!onlyDigits(phoneNumber.getText(), phoneNumber.getText().length())){
            feedbackErrorMessage+="Phone number should only contain digit numbers!\n";
        }

        if (userType.getText().isBlank()) {
            feedbackErrorMessage += "Agent or Administrator? \n";
        }

        if (!userType.getText().equals("Agent") && !userType.getText().equals("Administrator")){
            feedbackErrorMessage += "Please type exactly 'Agent' or 'Administrator' in user type field \n";
        }




        if (enable.getText().isBlank()) {
            feedbackErrorMessage += "User is enabled? True or False \n";
        }

        if (!enable.getText().equals("True") && !enable.getText().equals("False")){
            feedbackErrorMessage += "Please type exactly 'True' or 'False' in Enabled field \n";
        }

try {
    if (feedbackErrorMessage.isEmpty()) { //no errors


        RentAHomeMain.users.get(email.getText()).setUserType(userType.getText());
        RentAHomeMain.users.get(email.getText()).setUserName(name.getText());
        RentAHomeMain.users.get(email.getText()).setUserPassword(password.getText());
        RentAHomeMain.users.get(email.getText()).setUserEnabled(true);
        RentAHomeMain.users.get(email.getText()).setUserPhone(phoneNumber.getText());
        try {
            rentAHome.saveUsers();
        } catch (Exception e) {
            e.printStackTrace();
        }

        feedbackMain.setText("User updated Successfully");
        listAgent.getItems().clear();

    } else {
        feedbackMain.setText(feedbackErrorMessage);
    }
}
catch(NullPointerException e){
    feedbackMain.setText("Can't update since email does not exist\nEmails cannot be changed!");

        }
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        rentAHome=RentAHomeMain.getRentAHome();


        ObservableList<String> counties = FXCollections.observableArrayList("County","Waterford","Dublin","Cork");
        ObservableList<String> towns = FXCollections.observableArrayList("Town","Waterford","Dungarvan","Tramore","Sword","Lucan","Clondalkin","Cork","Midleton","Cobh");
        ObservableList<String> categories = FXCollections.observableArrayList("Category","Detached","Semi-Detached","Terraced","Apartment");
        ObservableList<String> prices = FXCollections.observableArrayList("Price","100K","200K","300K","500K","999K");


        county.setItems(counties);
        county.setValue("County");
        town.setItems(towns);
        town.setValue("Town");
        category.setItems(categories);
        category.setValue("Category");
        price.setItems(prices);
        price.setValue("Price");




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