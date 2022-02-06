module com.example.rentahome {
    requires javafx.controls;
    requires javafx.fxml;
    requires xstream;


    opens com.example.rentahome to javafx.fxml,xstream;
    exports com.example.rentahome;
}