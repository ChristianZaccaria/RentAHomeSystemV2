/*Developed by Christian Zaccaria (20092351)
 * CA 1 - Rent-A-Home system
 */

package com.example.rentahome;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RentAHomeMain extends Application {
    private static RentAHome rentAHome;
    public static HashMap<String,User> users;
    public static ArrayList<Property> available;
    private static User loggedInUser;

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static void setLoggedInUser(User loggedInUser) {
        RentAHomeMain.loggedInUser = loggedInUser;
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(RentAHomeMain.class.getResource("rentAHome-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 480);
        stage.setTitle("Rent-A-Home App");
        stage.setScene(scene);
        stage.show();
    }



    public static void main(String[] args) {
        rentAHome = new RentAHome();
        users = new HashMap<>();
        available= new ArrayList<>();

        /*users.put("chris@gmail.com", new User("Administrator", true, "Admin", "chris@gmail.com", "1234", "0329823"));
        users.put("joe@gmail.com", new User("Agent", true, "Joe", "joe@gmail.com", "12346", "0329823"));*/

        try{
            rentAHome.loadProperties();
            System.out.println("Properties have been loaded to the System");
        }

        catch (Exception e)
        {
            System.out.println("Error reading from file: " + e);
        }


        try{
            rentAHome.loadUsers();
            System.out.println("Users have been loaded to the System");
        }

        catch (Exception e)
        {
            System.out.println("Error reading from file: " + e);
        }

        launch();
    }

    public static RentAHome getRentAHome() {return rentAHome;}



}