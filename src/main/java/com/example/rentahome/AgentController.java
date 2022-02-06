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
import java.util.Optional;
import java.util.ResourceBundle;

public class AgentController implements Initializable {

    @FXML
    private ComboBox countySearch;

    @FXML
    private ComboBox townSearch;

    @FXML
    private ComboBox categorySearch;

    @FXML
    private ComboBox priceSearch;

    @FXML
    private TextArea listOfProperties;

    @FXML
    private TextField description;

    @FXML
    private TextField id;

    @FXML
    private TextField address;

    @FXML
    private TextField BER;

    @FXML
    private TextField eircode;

    @FXML
    private TextField agent;

    @FXML
    private TextField image;

    @FXML
    private Label mainFeedbackArea;

    @FXML
    private Label bottomFeedbackArea;

    @FXML
    private TextField emailUpdate;

    @FXML
    private PasswordField passwordUpdate;

    RentAHome rentAHome;



    @FXML
    void onClearListButtonClick(ActionEvent event) {
        listOfProperties.clear();
        mainFeedbackArea.setText("List of properties have been cleared!");
    }

    @FXML
    void onCreateButtonClick(ActionEvent event) {
        {
            //If textfields are blank, show that in feedback area
            if (description.getText().isBlank() ||
                    address.getText().isBlank() ||
                    BER.getText().isBlank() ||
                    eircode.getText().isBlank() ||
                    agent.getText().isBlank()) {
                mainFeedbackArea.setText("Required Fields not completed");
            }
            //if values below have not been changed from All to any other value, tell user to select a value for each of them
            else if(countySearch.getValue().equals("County") || townSearch.getValue().equals("Town") ||
                    categorySearch.getValue().equals("Category") ||
                    priceSearch.getValue().equals("Price")){
                mainFeedbackArea.setText("Please select property county, town, category and price range");
            }

            else if(image.getText().isBlank()){
                int propertyId = rentAHome.addProperty(description.getText(),
                        address.getText(), (String)categorySearch.getValue(), (String)countySearch.getValue(),
                        (String)townSearch.getValue(), BER.getText(), eircode.getText(), (String)priceSearch.getValue(),
                        agent.getText(), "C:\\Users\\Christian Zaccaria\\Documents\\WIT\\Modules\\Second Year\\Data Structures & Algorithms\\CA2\\Rent-A-Home CA2 - Christian Zaccaria (20092351)\\src\\main\\resources\\com\\example\\rentahome\\img\\default-image.jpg");
                mainFeedbackArea.setText("Product Created with id: "+ propertyId);
            }


            else {

                int propertyId = rentAHome.addProperty(description.getText(),
                        address.getText(), (String)categorySearch.getValue(), (String)countySearch.getValue(),
                        (String)townSearch.getValue(), BER.getText(), eircode.getText(), (String)priceSearch.getValue(),
                        agent.getText(), image.getText());
                mainFeedbackArea.setText("Product Created with id: "+ propertyId);
            }
        }
    }





    @FXML
    void onDeleteButtonClick(ActionEvent event) {

        //We try and see if the id is not null or a number, else we catch it
        try {
            int id1 = Integer.parseInt(id.getText());
            Property property = rentAHome.getPropertyDetailsById(id1);  //Returns link to the Product object (or null if it does not exist)

            if (property != null) {


                //Display the product information
                listOfProperties.setText(property.toString());
                description.setText(property.getDescription());
                address.setText(property.getAddress());
                BER.setText(property.getBER());
                eircode.setText(property.getEircode());
                agent.setText(property.getPropertyAgent());

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
                        mainFeedbackArea.setText("Property Deleted");
                        try {
                            rentAHome.saveProperties();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    mainFeedbackArea.setText("Delete Cancelled");
                }  //End of Alert check
            } //end of valid product id


            else { //deal with invalid product id entered -- null was returned
                mainFeedbackArea.setText("Invalid Property id");
            }
        }
        //if the id was not a number then we display the error.
        catch(NumberFormatException e){
            mainFeedbackArea.setText("Invalid Property id");

        }

        }

    @FXML
    void onDisplayAllButtonClick(ActionEvent event) {
        listOfProperties.setText(rentAHome.listAllProperties());
        mainFeedbackArea.setText("All available properties are displayed.");
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
        int id1;

        try{
            id1 =Integer.parseInt(id.getText());
        }
        catch(Exception e){
            mainFeedbackArea.setText("Error in id field\nPlease enter an Id NUMBER");
            return;
        }
        Property p = rentAHome.getPropertyDetailsById(id1); //Returns link to the Product object (or null if it does not exist)
        if (p != null) { //check that there is a product object with the id entered
            listOfProperties.setText(p.toString());
            description.setText(p.getDescription());
            address.setText(p.getAddress());
            BER.setText(p.getBER());
            eircode.setText(p.getEircode());
            agent.setText(p.getPropertyAgent());
            countySearch.setValue(p.getCounty());
            townSearch.setValue(p.getTown());
            categorySearch.setValue(p.getCategory());
            priceSearch.setValue(p.getPrice());
            mainFeedbackArea.setText("valid product id");
        } else {

            mainFeedbackArea.setText("Invalid Product id");
        }
    }

    @FXML
    protected void onClearDetailsButtonClick() {
        description.clear();
        address.clear();
        BER.clear();
        eircode.clear();
        agent.clear();
        countySearch.setValue("County");
        townSearch.setValue("Town");
        categorySearch.setValue("Category");
        priceSearch.setValue("Price");
        mainFeedbackArea.setText("Details have been cleared!");

    }

    @FXML
    void onSearchButtonClick(ActionEvent event) {
        if (countySearch.getValue().equals("County") ||
                townSearch.getValue().equals("Town") ||
                categorySearch.getValue().equals("Category") ||
                priceSearch.getValue().equals("Price"))
        {
            mainFeedbackArea.setText("Please select county, town, category, and price");
        }
        else {
            listOfProperties.setText(rentAHome.listAllPropertiesSpecific((String) countySearch.getValue(),
                    (String) townSearch.getValue(), (String) categorySearch.getValue(), (String) priceSearch.getValue()));
            mainFeedbackArea.setText("Search is done!");
        }
        }

    @FXML
    void onUpdateButtonClick(ActionEvent event) {
        try {
            if (description.getText().isBlank() ||
                    address.getText().isBlank() ||
                    BER.getText().isBlank() ||
                    eircode.getText().isBlank() ||
                    agent.getText().isBlank()) {
                mainFeedbackArea.setText("Required Fields not completed");
            } else {
                int id1 = Integer.parseInt(id.getText());
                if(rentAHome.getPropertyDetailsById(id1) !=null){
                rentAHome.editProperty(id1, description.getText(), address.getText(), BER.getText(), eircode.getText(),
                        agent.getText(),  (String) countySearch.getValue(), (String) townSearch.getValue(), (String) categorySearch.getValue(),
                        (String) priceSearch.getValue(), image.getText());
                mainFeedbackArea.setText("Product Updated");
                    listOfProperties.setText(rentAHome.listAllProperties());
                    try {
                        rentAHome.saveProperties();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else {
                mainFeedbackArea.setText("Id number doesn't exist");
                }
            }

        }
        catch(NumberFormatException e) {
            mainFeedbackArea.setText("Please enter property Index NUMBER and\nclick on the Read button");
        }
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        rentAHome=RentAHomeMain.getRentAHome();


        ObservableList<String> counties = FXCollections.observableArrayList("County","Waterford","Dublin","Cork");
        ObservableList<String> towns = FXCollections.observableArrayList("Town","Waterford","Dungarvan","Tramore","Sword","Lucan","Clondalkin","Cork","Midleton","Cobh");
        ObservableList<String> categories = FXCollections.observableArrayList("Category","Detached","Semi-Detached","Terraced","Apartment");
        ObservableList<String> prices = FXCollections.observableArrayList("Price","100K","200K","300K","500K","999K");


        countySearch.setItems(counties);
        countySearch.setValue("County");
        townSearch.setItems(towns);
        townSearch.setValue("Town");
        categorySearch.setItems(categories);
        categorySearch.setValue("Category");
        priceSearch.setItems(prices);
        priceSearch.setValue("Price");




    }



}