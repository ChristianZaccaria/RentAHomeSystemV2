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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RentAHomeController implements Initializable {

    @FXML
    private ComboBox<String> county;

    @FXML
    private ComboBox<String> town;

    @FXML
    private ComboBox<String> category;

    @FXML
    private ComboBox<String> price;

    @FXML
    private TextArea propertiesTextArea;


    @FXML
    private TableView tblView;

    @FXML
    private TableColumn countyColumn;

    @FXML
    private TableColumn townColumn;

    @FXML
    private TableColumn berColumn;

    @FXML
    private TableColumn priceColumn;

    @FXML
    private TableColumn categoryColumn;

    @FXML
    private TableColumn propertyAgentColumn;

    @FXML
    private Label feedback;

    RentAHome rentAHome;





    @FXML
    void onLoginButtonClick(ActionEvent event) throws IOException {

                Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();

                FXMLLoader fxmlLoader = new FXMLLoader(RentAHomeMain.class.getResource("login.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 640, 480);

                stage.setTitle("Login Page");
                stage.setScene(scene);
                stage.show();

    }

    /*@FXML
    protected void onClearButtonClick(ActionEvent event) {
        propertiesTextArea.clear();
        feedback.setText("List of properties has been cleared!");
    }*/

    @FXML
    void onDisplayAllButtonClick(ActionEvent event) {
        propertiesTextArea.setText(rentAHome.listAllProperties());
        feedback.setText("All available properties have been displayed above!");
    }


    @FXML
    protected void onRegisterButtonClick(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();

        FXMLLoader fxmlLoader = new FXMLLoader(RentAHomeMain.class.getResource("register.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 480);

        stage.setTitle("Register Page");
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    protected void onViewPropertyButtonClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("propertyView.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);


        PropertyViewController controller = loader.getController();
        Property productToDisplay =(Property)tblView.getSelectionModel().getSelectedItem(); //cast as Product
        if(productToDisplay == null) //sometimes no Product will have been selected
            return;

        controller.initData(productToDisplay);


        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    @FXML
    void onRefreshButtonClick(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();

        FXMLLoader fxmlLoader = new FXMLLoader(RentAHomeMain.class.getResource("rentAHome-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 480);

        stage.setTitle("Rent-A-Home Main Page");
        stage.setScene(scene);
        stage.show();



    }


  /*@FXML
   protected void onSearchButtonClick(ActionEvent event) {
        if (county.getValue().equals("County") ||
                town.getValue().equals("Town") ||
                category.getValue().equals("Category") ||
                price.getValue().equals("Price"))
        {
            feedback.setText("Please select county, town, category, and price");
        }
        else {

            propertiesTextArea.setText(rentAHome.listAllPropertiesSpecific((String) county.getValue(),
                    (String) town.getValue(), (String) category.getValue(), (String) price.getValue()));
            feedback.setText("Search is done!");
        }
    }*/

    public void onClickCounty(){
        ObservableList<Property> data = FXCollections.observableArrayList(rentAHome.getAvailableByCounty(county.getSelectionModel().getSelectedItem()));
        tblView.setItems(data);
        town.setValue("Town");
        category.setValue("Category");
        price.setValue("Price");
    }

    public void onClickTown(){
        ObservableList<Property> data = FXCollections.observableArrayList(rentAHome.getAvailableByTown(town.getSelectionModel().getSelectedItem()));
        tblView.setItems(data);
        county.setValue("County");
        category.setValue("Category");
        price.setValue("Price");
    }

    public void onClickCategory(){
        ObservableList<Property> data = FXCollections.observableArrayList(rentAHome.getAvailableByCategory(category.getSelectionModel().getSelectedItem()));
        tblView.setItems(data);
        county.setValue("County");
        town.setValue("Town");
        price.setValue("Price");
    }

    public void onClickPrice(){
        ObservableList<Property> data = FXCollections.observableArrayList(rentAHome.getAvailableByPrice(price.getSelectionModel().getSelectedItem()));
        tblView.setItems(data);
        county.setValue("County");
        town.setValue("Town");
        category.setValue("Category");
    }






    @Override
    public void initialize(URL location, ResourceBundle resources) {

        rentAHome = RentAHomeMain.getRentAHome();

        ObservableList<String> categories = FXCollections.observableArrayList("Category","Detached","Semi-Detached","Terraced","Apartment");
        ObservableList<String> counties = FXCollections.observableArrayList("County","Waterford","Dublin","Cork");
        ObservableList<String> towns = FXCollections.observableArrayList("Town","Waterford","Dungarvan","Tramore","Sword","Lucan","Clondalkin","Cork","Midleton","Cobh");
        ObservableList<String> prices = FXCollections.observableArrayList("Price","100K","200K","300K","500K","999K");


        category.setItems(categories);
        county.setItems(counties);
        town.setItems(towns);
        price.setItems(prices);
        category.setValue("Category");
        county.setValue("County");
        town.setValue("Town");
        price.setValue("Price");



        ObservableList<Property> data = FXCollections.observableArrayList(rentAHome.getAvailable());

        countyColumn.setCellValueFactory(new PropertyValueFactory<Property, String>("county"));
        townColumn.setCellValueFactory(new PropertyValueFactory<Property, String>("town"));
        berColumn.setCellValueFactory(new PropertyValueFactory<Property, String>("BER"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Property, String>("price"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<Property, String>("category"));
        propertyAgentColumn.setCellValueFactory(new PropertyValueFactory<Property, String>("propertyAgent"));


        tblView.setItems(data);

    }
}
