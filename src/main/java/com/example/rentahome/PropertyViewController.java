package com.example.rentahome;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class PropertyViewController {

    @FXML
    private Label id;

    @FXML
    private Label description;

    @FXML
    private Label address;

    @FXML
    private Label category;

    @FXML
    private Label county;

    @FXML
    private Label town;

    @FXML
    private Label ber;

    @FXML
    private Label eircode;

    @FXML
    private Label price;

    @FXML
    private Label propertyAgent;

    @FXML
    private ImageView photo;

    @FXML
    void changeScreenButtonPushed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("rentAHome-view.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    public void initData(Property property)
    {


        File imageFile = new File(property.getImageName());
        String fileLocation = imageFile.toURI().toString();
        Image img = new Image(fileLocation);

        photo.setImage(img);

        id.setText(""+property.getId());
        description.setText(property.getDescription());
        address.setText(property.getAddress());
        category.setText(property.getCategory());
        county.setText(property.getCounty());
        town.setText(property.getTown());
        ber.setText(property.getBER());
        eircode.setText(property.getEircode());
        price.setText(property.getPrice());
        propertyAgent.setText(property.getPropertyAgent());


    }


}
