module com.example.pms {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;
    requires java.desktop;

    opens com.example.pms to javafx.fxml;
    exports com.example.pms;
}